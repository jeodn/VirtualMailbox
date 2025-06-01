"""
### Explanation of `app.py`:

- **`CORS(app)`**: Enables Cross-Origin Resource Sharing so that your SvelteKit front end (likely running on `localhost:5173`) can call this Flask API (on `localhost:5000`) without CORS errors.
- **In-memory dictionaries (`USERS`, `MAIL`, `REQUESTS`)**:
    - Each key is the `user_id` (an integer).
    - `USERS[user_id]` → a `User` object.
    - `MAIL[user_id]` → an array of `Mail` objects for that user.
    - `REQUESTS[user_id]` → an array of `UserRequest` objects for that user.
- **Endpoints**:
    1. `GET /api/users` → returns `[{id, name, email, avatar}, …]`.
    2. `GET /api/users/<user_id>` → returns a single `User` or 404.
    3. `GET /api/users/<user_id>/mail` → returns `Mail[]` for that user.
    4. `GET /api/users/<user_id>/requests` → returns `UserRequest[]` for that user.
    5. `POST /api/users/<user_id>/mail` → you send a JSON payload (`date`, `to`, `from`, `type`, `thumbnail`, optionally `contentImage`), and it returns the newly created `Mail` object with a new `id`.
    6. `PATCH /api/users/<user_id>/mail/<mail_id>` → expects a JSON body like `{ "opened": true, "contentImage": "http://…" }` (you can send either or both fields). It updates that mail item and returns the updated object.
    7. `DELETE /api/users/<user_id>` → deletes the user and all their mail/requests. Returns a simple message.
"""

# app.py
from flask import Flask, jsonify, request, abort
from flask_cors import CORS

app = Flask(__name__)
CORS(app)  # allow all origins by default (for dev only)

# In-memory “database” for demonstration
# You can replace this with a real DB (SQLite, PostgreSQL, etc.) later.
USERS = {
    101: {
        "id": 101,
        "name": "Alice Johnson",
        "email": "alice@example.com",
        "avatar": "/img/users/user-101.jpg"
    },
    102: {
        "id": 102,
        "name": "Bob Smith",
        "email": "bob@corp.com",
        "avatar": "/img/users/user-102.jpg"
    },
}

# Each user’s mail is stored in a list under their user ID
MAIL = {
    101: [
        {
            "id": 201,
            "date": "May 29",
            "to": "alice@example.com",
            "from": "friend@site.com",
            "type": "Personal",
            "starred": False,
            "selected": False,
            "opened": False,
            "thumbnail": "/img/thumb-alice-1.jpg",
            "contentImage": None
        },
        {
            "id": 202,
            "date": "May 28",
            "to": "alice@example.com",
            "from": "store@shop.com",
            "type": "Promotion",
            "starred": True,
            "selected": False,
            "opened": True,
            "thumbnail": "/img/thumb-alice-2.jpg",
            "contentImage": "/img/opened-alice-2.jpg"
        }
    ],
    102: [
        {
            "id": 301,
            "date": "June 1",
            "to": "bob@corp.com",
            "from": "newsletter@site.com",
            "type": "Newsletter",
            "starred": False,
            "selected": False,
            "opened": False,
            "thumbnail": "/img/thumb-bob-1.jpg",
            "contentImage": None
        }
    ],
}

# Each user’s pending requests
REQUESTS = {
    101: [
        { "id": 401, "text": "Shred Mail 202" },
        { "id": 402, "text": "Trash Mail 201" }
    ],
    102: [
        { "id": 403, "text": "Open Mail 301" }
    ],
}


@app.route("/api/users", methods=["GET"])
def list_users():
    """Return a list of all users."""
    return jsonify(list(USERS.values())), 200


@app.route("/api/users/<int:user_id>", methods=["GET"])
def get_user(user_id):
    """Return a single user by ID."""
    user = USERS.get(user_id)
    if not user:
        return jsonify({"error": "User not found"}), 404
    return jsonify(user), 200


@app.route("/api/users/<int:user_id>/mail", methods=["GET"])
def get_user_mail(user_id):
    """Return the mail array for a given user."""
    if user_id not in USERS:
        return jsonify({"error": "User not found"}), 404
    return jsonify(MAIL.get(user_id, [])), 200


@app.route("/api/users/<int:user_id>/requests", methods=["GET"])
def get_user_requests(user_id):
    """Return pending requests for this user."""
    if user_id not in USERS:
        return jsonify({"error": "User not found"}), 404
    return jsonify(REQUESTS.get(user_id, [])), 200


@app.route("/api/users/<int:user_id>/mail", methods=["POST"])
def create_mail_for_user(user_id):
    """Add a new mail item to a user’s mail list."""
    if user_id not in USERS:
        return jsonify({"error": "User not found"}), 404

    data = request.get_json()
    required_fields = ["date", "to", "from", "type", "thumbnail"]
    if not data or any(field not in data for field in required_fields):
        return jsonify({"error": "Missing required mail fields"}), 400

    # Build a new Mail object
    new_id = max((m["id"] for m in MAIL.get(user_id, [])), default=0) + 1
    new_mail = {
        "id": new_id,
        "date": data["date"],
        "to": data["to"],
        "from": data["from"],
        "type": data["type"],
        "starred": False,
        "selected": False,
        "opened": False,
        "thumbnail": data["thumbnail"],
        "contentImage": data.get("contentImage", None),
    }

    MAIL.setdefault(user_id, []).append(new_mail)
    return jsonify(new_mail), 201


@app.route("/api/users/<int:user_id>/mail/<int:mail_id>", methods=["PATCH"])
def update_mail_for_user(user_id, mail_id):
    """Update a single mail item (e.g. set opened=true or add contentImage)."""
    if user_id not in USERS:
        return jsonify({"error": "User not found"}), 404

    user_mail_list = MAIL.get(user_id, [])
    mail_item = next((m for m in user_mail_list if m["id"] == mail_id), None)
    if not mail_item:
        return jsonify({"error": "Mail not found"}), 404

    data = request.get_json()
    # Allow updating “opened” and/or “contentImage” fields
    if "opened" in data:
        mail_item["opened"] = bool(data["opened"])
    if "contentImage" in data:
        mail_item["contentImage"] = data["contentImage"]

    return jsonify(mail_item), 200


@app.route("/api/users/<int:user_id>", methods=["DELETE"])
def delete_user(user_id):
    """Delete a user and all associated data."""
    if user_id not in USERS:
        return jsonify({"error": "User not found"}), 404

    # (In a real app: verify a secure token or admin password here)
    # For now, we simply delete from all dicts:
    USERS.pop(user_id, None)
    MAIL.pop(user_id, None)
    REQUESTS.pop(user_id, None)
    return jsonify({"message": f"User {user_id} deleted"}), 200


if __name__ == "__main__":
    # Run on http://localhost:5000 by default
    app.run(debug=True)
