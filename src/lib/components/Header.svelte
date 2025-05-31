<script>
    import { goto } from '$app/navigation';

    // State
    let filterOpen = $state(false);
    let settingsOpen = $state(false);
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
    function openSelected() { 
        // Temporary: always go to /mail/12
        goto('/mail/12');
    }
    function shredSelected() { /* your logic */ }
    function starSelected() {
        mails = mails.map(m =>
        m.selected ? { ...m, starred: !m.starred } : m
        );
    }

    /** 
     * Svelte action: calls `handler` when a click happens outside `node`
     * Usage in markup: <div use:clickOutside={() => filterOpen = false}>…</div>
     * @param {HTMLElement} node - The DOM node to watch
     * @param {() => void} handler - Function to call when clicking outside
     * @returns {{ destroy(): void }}
     */
    function clickOutside(node, handler) {
        // @ts-ignore
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


<!-- Top bar: search + settings -->
    <header class="flex items-center justify-between bg-white px-6 py-3 border-b">
      <div class="relative flex items-center w-1/2">
        <input
          type="text"
          placeholder="Search mail…"
          class="w-full border rounded-full px-4 py-2 focus:outline-none focus:ring"
        />
        <button onclick={toggleFilter} class="ml-2 p-2 rounded-full hover:bg-gray-100">
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
        <button onclick={toggleSettings} class="p-2 rounded-full hover:bg-gray-100">
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