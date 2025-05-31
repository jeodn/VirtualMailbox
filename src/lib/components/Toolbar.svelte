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

<!-- Toolbar -->
<div class="flex items-center bg-white px-6 py-2 border-b space-x-4">
    <button onclick={toggleSelectAll} class="p-2 rounded hover:bg-gray-100">
    <!-- select all icon -->
    <input type="checkbox" class="h-5 w-5"/>
    </button>
    <button onclick={openSelected} class="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600">Open</button>
    <button onclick={shredSelected} class="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600">Shred</button>
    <button onclick={starSelected} class="px-3 py-1 bg-yellow-400 text-white rounded hover:bg-yellow-500">Star</button>
</div>
