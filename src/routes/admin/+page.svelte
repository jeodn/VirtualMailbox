<script lang="ts">
  import { goto } from '$app/navigation';
  import Sidebar from '$lib/components/Sidebar.svelte';
  import Header from '$lib/components/Header.svelte';
  import Toolbar from '$lib/components/Toolbar.svelte';
  import UserCard from '$lib/components/UserCard.svelte';
	import AdminHeader from '$lib/components/AdminHeader.svelte';

  /** @typedef {import('$lib/types').User} User */
  /** @type {User[]} */
  let users = [
    {
      id: 101,
      name: 'Alice Johnson',
      email: 'alice@example.com',
      avatar: '/img/users/alice.jpg',  // put these images in /static/img/users/
    },
    {
      id: 102,
      name: 'Bob Smith',
      email: 'bob@corp.com',
      avatar: '/img/users/bob.png',
    },
    // …more users…
  ];

  /** Navigate to that user’s admin page */
  function goToUser(userId: number) {
    // In SvelteKit, clicking should take us to /admin/user/ID
    goto(`/admin/user/${userId}`);
  }
</script>

<div class="flex h-screen bg-gray-50">

  <div class="flex-1 flex flex-col">
    <AdminHeader />
    
    <div class="flex-1 flex overflow-hidden">
      <!-- Centered column for user cards -->
      <div class="mx-auto w-full max-w-md overflow-y-auto bg-white border border-gray-200 rounded-lg mt-8 mb-8 shadow-sm">
        <h2 class="text-xl font-semibold px-6 py-4 border-b">All Users</h2>

        {#if users.length > 0}
          <div class="divide-y divide-gray-200">
            {#each users as user (user.id)}
              <UserCard {user} onClick={() => goToUser(user.id)} />
            {/each}
          </div>
        {:else}
          <p class="p-6 text-center text-gray-500">No users found.</p>
        {/if}
      </div>
    </div>
  </div>
</div>
