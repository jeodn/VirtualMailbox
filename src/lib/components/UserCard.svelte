<script lang="ts">
  /** 
   * @typedef {import('$lib/types').User} User 
   * (this is imported from your `types.ts` file—see below)
   */

  /** @type {User} */
  export let user;

  /** @type {() => void} */
  export let onClick = () => {};
</script>

<button
  type="button"
  class="w-full text-left border-b p-4 hover:bg-gray-50 focus:outline-none"
  on:click={onClick}
>
  <div class="flex items-center space-x-3">
    <!-- If user.avatar exists, show it, otherwise placeholder -->
    {#if user.avatar}
      <img
        src={user.avatar}
        alt="avatar"
        class="w-12 h-12 object-cover rounded-full"
      />
    {:else}
      <div class="w-12 h-12 bg-gray-200 rounded-full flex items-center justify-center">
        <svg class="w-6 h-6 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
          <path
            d="M10 10a4 4 0 100-8 4 4 0 000 8zm-2 2a6 6 0 00-6 6h16a6 6 0 00-6-6H8z"
          />
        </svg>
      </div>
    {/if}

    <div class="flex-1">
      <div class="text-sm font-semibold">{user.name}</div>
      <div class="text-xs text-gray-500">{user.email}</div>
    </div>
  </div>
</button>
