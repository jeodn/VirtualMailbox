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

    function goToPrimary() {
        goto('/inbox');
    }

    function goToStarred() {
        goto('/inbox');
    }

    function goToTrash() {
        goto('/inbox');
    }

</script>

<!-- Sidebar -->
  <nav class="w-60 bg-white border-r flex-shrink-0 p-4 space-y-4">
    <button onclick={goToPrimary} class="w-full text-left px-3 py-2 rounded hover:bg-gray-100">Primary</button>
    <button class="w-full text-left px-3 py-2 rounded hover:bg-gray-100">Starred</button>
    <button class="w-full text-left px-3 py-2 rounded hover:bg-gray-100">Trash</button>
  </nav>