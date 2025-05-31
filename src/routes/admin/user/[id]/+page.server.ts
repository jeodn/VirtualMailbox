// This file runs _only_ on the server, never in the browser.

import type { PageServerLoad } from './$types';
import type { User, Mail, UserRequest } from '$lib/types';

/**
 * The `load` function here has access to:
 *  - params.id            → the [id] from the URL
 *  - fetch (server‐side fetch)
 *  - and ANY server‐only APIs (database, process.env, etc.)
 */
export const load: PageServerLoad = async ({ params, fetch }) => {
  const userId = Number(params.id);

  // ------------------------
  // ‣ Example: mock data fetch
  // ------------------------
  // In a real app, replace entirely with something like:
  //   const user = await db.user.findUnique({ where: { id: userId } });
  //   if (!user) throw error(404, 'User not found');
  //   const mail = await db.mail.findMany({ where: { userId } });
  //   const requests = await db.request.findMany({ where: { userId, status: 'pending' } });
  //
  // For demo, we keep your mock:
  const user: User = {
    id: userId,
    name: userId === 101 ? 'Alice Johnson' : `User #${userId}`,
    email: userId === 101 ? 'alice@example.com' : `user${userId}@example.com`,
    avatar: `/img/users/user-${userId}.jpg`
  };

  // Mock: that user’s mail
  const mail: Mail[] = [
    {
      id: 201,
      date: 'May 29',
      to: user.email,
      from: 'friend@site.com',
      type: 'Personal',
      starred: false,
      selected: false,
      opened: false,
      thumbnail: '/img/thumb-alice-1.jpg',
      contentImage: null
    },
    {
      id: 202,
      date: 'May 28',
      to: user.email,
      from: 'store@shop.com',
      type: 'Promotion',
      starred: true,
      selected: false,
      opened: true,
      thumbnail: '/img/thumb-alice-2.jpg',
      contentImage: '/img/opened-alice-2.jpg'
    }
  ];

  // Mock: that user’s pending requests
  const requests: UserRequest[] = [
    { id: 301, text: 'Shred Mail 202' },
    { id: 302, text: 'Trash Mail 201' }
  ];

  // If you wanted: guard against a “nonexistent user”
  // if (!user) {
  //   throw error(404, 'User not found');
  // }

  return {
    user,
    mail,
    requests
  };
};
