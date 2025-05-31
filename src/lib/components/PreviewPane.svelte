<script>
  /** @typedef {import('$lib/types').Mail} Mail */

    let { selectedMail, currentTab } = $props();

</script>

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

<style>
  button.selected {
    border-bottom: 2px solid #3b82f6;
    color: #1d4ed8;
  }
</style>
