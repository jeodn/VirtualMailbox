<script>
    import { page } from '$app/state';
    import Sidebar from '$lib/components/Sidebar.svelte';
    import Header from '$lib/components/Header.svelte';
    import Toolbar from '$lib/components/Toolbar.svelte';

    $: mailId = page.params.id;
</script>

<!--Main screen-->
<div class="flex h-screen bg-gray-50">

    <Sidebar />

    <div class="flex-1 flex flex-col">
    

    <Header />

    <Toolbar />

    <h1>Viewing mail #{mailId}</h1>

        <div class="flex-1 overflow-auto bg-white">
        <table class="w-full text-left">
            <thead class="bg-gray-100">
                <tr>
                    <th class="px-4 py-2">Overview</th>
                    <th class="px-4 py-2">Preview</th>
                </tr>
                </thead>
            <tbody>
                <tr class="hover:bg-gray-50">
                    <td class="px-4 py-2 text-sm">
                        <p>ME TALKING !!!!!!</p>

                    </td>
                    <td class="px-4 py-2 text-sm">
                        <img src="https://cdn.discordapp.com/attachments/835643182367899648/1377715833823821925/IMG_1257.png?ex=683aa21b&is=6839509b&hm=21a0660bc545715894b897bc37d518ce338c8af683f1aff1e385d7e4e687a77d&" alt="swag">
                    </td>
                </tr>
            </tbody>
        </table>
        </div>

    </div>

</div>