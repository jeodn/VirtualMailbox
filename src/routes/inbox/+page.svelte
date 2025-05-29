<script>
  // State
  let filterOpen = false;
  let settingsOpen = false;
  let mails = [
    { id: 1, date: 'May 28', to: 'alice@example.com', from: 'bob@corp.com', type: 'Promotion', starred: false, selected: false },
    { id: 2, date: 'May 27', to: 'you@domain.com', from: 'newsletter@site.com', type: 'Newsletter', starred: true, selected: false },
    // …more dummy items
  ];

  // Actions
  function toggleFilter() { filterOpen = !filterOpen; }
  function toggleSettings() { settingsOpen = !settingsOpen; }

  function toggleSelectAll() {
    const all = mails.every(m => m.selected);
    mails = mails.map(m => ({ ...m, selected: !all }));
  }
  function openSelected() { /* your logic */ }
  function shredSelected() { /* your logic */ }
  function starSelected() {
    mails = mails.map(m =>
      m.selected ? { ...m, starred: !m.starred } : m
    );
  }

  /** 
   * Svelte action: calls `handler` when a click happens outside `node`
   * Usage in markup: <div use:clickOutside={() => filterOpen = false}>…</div>
   */
  function clickOutside(node, handler) {
    const onClick = e => {
      if (!node.contains(e.target)) handler();
    };
    document.addEventListener('click', onClick, true);
    return {
      destroy() {
        document.removeEventListener('click', onClick, true);
      }
    };
  }
</script>

<div class="flex h-screen bg-gray-50">

  <!-- Sidebar -->
  <nav class="w-60 bg-white border-r flex-shrink-0 p-4 space-y-4">
    <button class="w-full text-left px-3 py-2 rounded hover:bg-gray-100">Primary</button>
    <button class="w-full text-left px-3 py-2 rounded hover:bg-gray-100">Starred</button>
    <button class="w-full text-left px-3 py-2 rounded hover:bg-gray-100">Trash</button>
  </nav>

  <!-- Main area -->
  <div class="flex-1 flex flex-col">

    <!-- Top bar: search + settings -->
    <header class="flex items-center justify-between bg-white px-6 py-3 border-b">
      <div class="relative flex items-center w-1/2">
        <input
          type="text"
          placeholder="Search mail…"
          class="w-full border rounded-full px-4 py-2 focus:outline-none focus:ring"
        />
        <button on:click={toggleFilter} class="ml-2 p-2 rounded-full hover:bg-gray-100">
          <!-- filter icon -->
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none"
               viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round"
               stroke-linejoin="round" stroke-width="2"
               d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2a1 1 0
                  01-.293.707L14 13.414V19a1 1 0
                  01-1.447.894l-4-2A1 1 0 018 17v-3.586L3.293
                  6.707A1 1 0 013 6V4z"/></svg>
        </button>

        {#if filterOpen}
          <div use:clickOutside={() => (filterOpen = false)}
               class="absolute mt-2 bg-white border rounded shadow-lg p-4 w-64">
            <h4 class="font-semibold mb-2">Filters</h4>
            <label class="block mb-1">
              <input type="checkbox" /> Unread
            </label>
            <label class="block mb-1">
              <input type="checkbox" /> Starred
            </label>
            <label class="block">
              <input type="checkbox" /> With Attachments
            </label>
          </div>
        {/if}
      </div>

      <div class="relative">
        <!-- toggle button -->
        <button on:click={toggleSettings} class="p-2 rounded-full hover:bg-gray-100">
            <!-- settings icon SVG -->
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
                viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round"
                stroke-linejoin="round" stroke-width="2"
                d="M12 6v.01M12 12v.01M12 18v.01
                    M8 6h.01M8 12h.01M8 18h.01
                    M16 6h.01M16 12h.01M16 18h.01"/></svg>
        </button>

        {#if settingsOpen}
            <!-- popup uses clickOutside to auto-close -->
            <div
            use:clickOutside={() => (settingsOpen = false)}
            class="absolute right-0 mt-2 bg-white border rounded shadow-lg w-40"
            >
            <a href="/profile" class="block px-4 py-2 hover:bg-gray-100">Profile</a>
            <a href="/preferences" class="block px-4 py-2 hover:bg-gray-100">Preferences</a>
            <a href="/signout" class="block px-4 py-2 hover:bg-gray-100">Sign out</a>
            </div>
        {/if}
        </div>
    </header>

    <!-- Toolbar -->
    <div class="flex items-center bg-white px-6 py-2 border-b space-x-4">
      <button on:click={toggleSelectAll} class="p-2 rounded hover:bg-gray-100">
        <!-- select all icon -->
        <input type="checkbox" class="h-5 w-5"/>
      </button>
      <button on:click={openSelected} class="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600">Open</button>
      <button on:click={shredSelected} class="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600">Shred</button>
      <button on:click={starSelected} class="px-3 py-1 bg-yellow-400 text-white rounded hover:bg-yellow-500">Star</button>
    </div>

    <!-- Mail list -->
    <div class="flex-1 overflow-auto bg-white">
      <table class="w-full text-left">
        <thead class="bg-gray-100">
          <tr>
            <th class="px-4 py-2"></th>
            <th class="px-4 py-2">Date</th>
            <th class="px-4 py-2">To</th>
            <th class="px-4 py-2">From</th>
            <th class="px-4 py-2">Type</th>
          </tr>
        </thead>
        <tbody>
          {#each mails as mail (mail.id)}
            <tr class="hover:bg-gray-50">
              <td class="px-4 py-2">
                <input type="checkbox" bind:checked={mail.selected} class="h-4 w-4" />
              </td>
              <td class="px-4 py-2 text-sm">{mail.date}</td>
              <td class="px-4 py-2 text-sm">{mail.to}</td>
              <td class="px-4 py-2 text-sm">{mail.from}</td>
              <td class="px-4 py-2 text-sm flex items-center space-x-1">
                {mail.type}
                {#if mail.starred}
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 text-yellow-500" fill="currentColor" viewBox="0 0 20 20">
                    <path d="M9.049 2.927c...z" />
                  </svg>
                {/if}
              </td>
            </tr>
          {/each}
        </tbody>
      </table>
    </div>
  </div>
</div>

<style>
  /* ensure table rows don’t overflow */
  table { border-collapse: collapse; }
</style>
