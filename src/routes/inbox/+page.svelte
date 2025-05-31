<script>
  import { goto } from '$app/navigation';
  import Sidebar from '../Sidebar.svelte';
  import Header from '../Header.svelte';
  import Toolbar from '../Toolbar.svelte';
  import MailCard from '../MailCard.svelte';
  import PreviewPane from '../PreviewPane.svelte';

  /** @typedef {{
    id: number;
    date: string;
    to: string;
    from: string;
    type: string;
    starred: boolean;
    selected: boolean;
    opened: boolean;
    thumbnail: string;
    contentImage: string | null;
  }} Mail */

  /** @type {Mail[]} */
  let mails = $state([
    { id: 1, date: 'May 28', to: 'alice@example.com', from: 'bob@corp.com', type: 'Promotion', starred: false, selected: false, opened: true, thumbnail: '/img/thumb1.jpg', contentImage: '/img/opened1.jpg' },
    { id: 2, date: 'May 27', to: 'you@domain.com', from: 'newsletter@site.com', type: 'Newsletter', starred: true, selected: false, opened: false, thumbnail: '/img/thumb2.jpg', contentImage: null },
  ]);

  /** @type {Mail} */
  let selectedMail = $state({
    id: 0,
    date: '',
    to: '',
    from: '',
    type: '',
    starred: false,
    selected: false,
    opened: false,
    thumbnail: '',
    contentImage: null
  });
  let currentTab = $state('overview');

  /** @param {Mail} mail */
  function selectMail(mail) {
    Object.assign(selectedMail, mail);
    currentTab = 'overview';
  }
</script>

<div class="flex h-screen bg-gray-50">
  <Sidebar />

  <div class="flex-1 flex flex-col">
    <Header />
    <Toolbar />

    <div class="flex flex-1 overflow-hidden">
      <!-- Left Column: Mail list -->
      <div class="w-1/3 overflow-y-auto border-r border-gray-200 bg-white">
        {#each mails as mail (mail.id)}
          <MailCard {mail} onClick={() => selectMail(mail)}></MailCard>
        {/each}
      </div>

      <!-- Right Column: Mail preview -->
      <div class="flex-1 flex flex-col">
        <div class="border-b flex">
          <button
            onclick={() => currentTab = 'overview'}
            class="px-4 py-2 text-sm font-semibold"
            class:selected={currentTab === 'overview'}
          >Overview</button>
          <button
            onclick={() => currentTab = 'content'}
            class="px-4 py-2 text-sm font-semibold"
            class:selected={currentTab === 'content'}
          >Content</button>
        </div>

        <div class="flex-1 overflow-auto p-4">
          {#if selectedMail}
            <!-- Destructure safely inside the block -->
            {@const mail = selectedMail}
            {#if currentTab === 'overview'}
              <div>
                <img src={mail.thumbnail} alt="enlarged preview" class="w-64 h-auto mb-4 rounded" />
                <ul class="text-sm space-y-1">
                  <li><strong>Date:</strong> {mail.date}</li>
                  <li><strong>Recipient:</strong> {mail.to}</li>
                  <li><strong>Sender:</strong> {mail.from}</li>
                  <li><strong>Type:</strong> {mail.type}</li>
                  <li><strong>Starred:</strong> {mail.starred ? 'Yes' : 'No'}</li>
                </ul>
              </div>
            {:else if currentTab === 'content'}
              {#if mail.opened && mail.contentImage}
                <img src={mail.contentImage} alt="opened content" class="w-full h-auto rounded" />
              {:else}
                <p class="text-gray-500 italic">Unopened package</p>
              {/if}
            {/if}
          {:else}
            <p class="text-gray-400 italic">No mail selected</p>
          {/if}
        </div>
      </div>
          </div>
        </div>
      </div>

<style>
  button.selected {
    border-bottom: 2px solid #3b82f6;
    color: #1d4ed8;
  }
</style>
