<!-- src/routes/+page.svelte -->
<script>
  import { onMount } from 'svelte';
  import { goto } from '$app/navigation';

  onMount(() => {
    goto('/login');
  });
</script>

<p>Redirecting to login...</p>