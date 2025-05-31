<script lang="ts">
    /** @typedef {import('$lib/types').Mail} Mail */

    let { mail, onClick } = $props();
</script>

<button
  type="button"
  onclick={onClick}
  class="w-full text-left border-b p-4 hover:bg-gray-50 focus:outline-none"
>
  <div class="flex items-center space-x-3">
    <img src={mail.thumbnail} alt="thumbnail" class="w-16 h-16 object-cover rounded" />
    <div class="flex-1">
      <div class="text-sm font-semibold">{mail.date}</div>
      <div class="text-xs text-gray-500">To: {mail.to}</div>
      <div class="text-xs text-gray-500">From: {mail.from}</div>
    </div>
  </div>
</button>
