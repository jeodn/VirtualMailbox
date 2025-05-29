<script>
  let email = '';
  let password = '';
  let password_repeat = '';

  function handleLogin() {
    // Replace this with real authentication logic
    console.log('Logging in with:', { email, password });
  }
</script>

<div class="min-h-screen flex items-center justify-center bg-gray-100 px-4">
  <div class="max-w-md w-full bg-white p-8 rounded-2xl shadow-md">
    <h2 class="text-2xl font-bold text-center mb-6">Create Your Account</h2>

    <form on:submit|preventDefault={handleLogin} class="space-y-6">
      <div>
        <label for="email" class="block text-sm font-medium text-gray-700">Email address</label>
        <input
          id="email"
          type="email"
          bind:value={email}
          required
          class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-xl shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
        />
      </div>

      <div>
        <label for="password" class="block text-sm font-medium text-gray-700">Password</label>
        <input
          id="password"
          type="password"
          bind:value={password}
          required
          class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-xl shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
        />
      </div>

      <div>
        <label for="password" class="block text-sm font-medium text-gray-700">Repeat Password</label>
        <input
          id="password_repeat"
          type="password_repeat"
          bind:value={password_repeat}
          required
          class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-xl shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
        />
      </div>

      <button
        type="submit"
        class="w-full flex justify-center py-2 px-4 border border-transparent rounded-xl shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none"
      >
        Sign Up
      </button>
    </form>

    <p class="mt-4 text-center text-sm text-gray-600">
      Already have an account?
      <a href="/login" class="text-indigo-600 hover:underline">Log in</a>
    </p>
  </div>
</div>