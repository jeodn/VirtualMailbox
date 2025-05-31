<script lang="ts">
import { goto } from '$app/navigation';
import Sidebar from '$lib/components/Sidebar.svelte';
import Header from '$lib/components/Header.svelte';
import Toolbar from '$lib/components/Toolbar.svelte';
import MailCard from '$lib/components/MailCard.svelte';

import type { User, Mail, UserRequest } from '$lib/types';
    import AdminHeader from '$lib/components/AdminHeader.svelte';

/** 
 * The `data` prop is automatically injected by SvelteKit,
 * reflecting whatever the server‐side `load` returned.
 */
export let data: {
    user: User;
    mail: Mail[];
    requests: UserRequest[];
};

// Destructure so we can use `user`, `mail`, and `requests` in the template
let { user, mail, requests } = data;

function deleteUser() {
    const password = prompt('Enter admin password to delete this user:');
    if (!password) return;
    // In a real app, you’d call your server endpoint:
    //   await fetch(`/api/admin/users/${user.id}`, { method: 'DELETE', body: JSON.stringify({ password }) });
    alert(`(Pretend we deleted ${user.name} with password "${password}".)`);
    goto('/admin');
}

function fulfillRequest(reqId: string | number) {
    alert(`(Fulfilled request ${reqId} for user ${user.name}.)`);
    requests = requests.filter((r) => r.id !== reqId);
}

function addMail() {
    const newMail: Mail = {
    id: Math.floor(Math.random() * 100000), 
    date: new Date().toLocaleDateString('en-US', { month: 'short', day: 'numeric' }),
    to: user.email,
    from: prompt('Enter "from" address:') || 'noreply@example.com',
    type: prompt('Enter mail type:') || 'General',
    starred: false,
    selected: false,
    opened: false,
    thumbnail: '/img/default-thumb.png',
    contentImage: null
    };
    mail = [newMail, ...mail];
}

function updateMail(mailId: string | number) {
    const url = prompt('Enter URL for the opened image:');
    if (!url) return;
    mail = mail.map((m) =>
    m.id === mailId ? { ...m, opened: true, contentImage: url } : m
    );
}
</script>

<div class="flex h-screen bg-gray-50">



<div class="flex-1 flex flex-col">
    <AdminHeader />
    <div class="flex-1 flex overflow-hidden">
    <div class="mx-auto w-full max-w-2xl overflow-y-auto bg-white border border-gray-200 rounded-lg mt-8 mb-8 shadow-md">
        {#if user}
        <!-- User header -->
        <div class="px-6 py-4 flex items-center border-b">
            {#if user.avatar}
            <img
                src={user.avatar}
                alt="avatar"
                class="w-16 h-16 object-cover rounded-full mr-4"
            />
            {:else}
            <div class="w-16 h-16 bg-gray-200 rounded-full mr-4 flex items-center justify-center">
                <svg class="w-8 h-8 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                <path d="M10 10a4 4 0 100-8 4 4 0 000 8zm-2 2a6 6 0 00-6 6h16a6 6 0 00-6-6H8z" />
                </svg>
            </div>
            {/if}

            <div>
            <h2 class="text-2xl font-semibold">{user.name}</h2>
            <p class="text-sm text-gray-500">{user.email}</p>
            </div>

            <button
            on:click={deleteUser}
            class="ml-auto bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 focus:outline-none"
            >
            Delete User
            </button>
        </div>

        <!-- “Add Mail” section -->
        <div class="px-6 py-4 border-b flex justify-between items-center">
            <h3 class="text-lg font-medium">Mail</h3>
            <button
            on:click={addMail}
            class="bg-green-500 text-white px-3 py-1 text-sm rounded hover:bg-green-600 focus:outline-none"
            >
            + Add Mail
            </button>
        </div>

        <!-- List of MailCards -->
        <div class="p-6 space-y-4">
            {#if mail.length > 0}
            <div class="grid grid-cols-1 gap-4">
                {#each mail as m (m.id)}
                <div class="flex items-center justify-between">
                    <MailCard {mail} onClick={() => updateMail(m.id)} />
                    <button
                    on:click={() => updateMail(m.id)}
                    class="ml-4 text-blue-600 hover:underline text-sm"
                    >
                    Edit
                    </button>
                </div>
                {/each}
            </div>
            {:else}
            <p class="text-gray-500 italic">No mail for this user.</p>
            {/if}
        </div>

        <!-- Pending Requests -->
        <div class="px-6 pb-6 border-t">
            <h3 class="text-lg font-medium mb-2">Pending Requests</h3>
            {#if requests.length > 0}
            <ul class="space-y-2">
                {#each requests as req (req.id)}
                <li class="flex items-center justify-between bg-gray-50 border border-gray-200 rounded p-3">
                    <span class="text-sm">{req.text}</span>
                    <button
                    on:click={() => fulfillRequest(req.id)}
                    class="bg-blue-500 text-white px-3 py-1 text-sm rounded hover:bg-blue-600 focus:outline-none"
                    >
                    Fulfill
                    </button>
                </li>
                {/each}
            </ul>
            {:else}
            <p class="text-gray-500 italic">No pending requests.</p>
            {/if}
        </div>
        {:else}
        <div class="p-6 text-center text-red-500">Error: User not found.</div>
        {/if}
    </div>
    </div>
</div>
</div>
