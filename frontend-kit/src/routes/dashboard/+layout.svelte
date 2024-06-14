<script>
	import Navbar from '$lib/components/Navbar.svelte';
	import { navigating } from '$app/stores';
</script>

<header>
	<Navbar />
</header>
<main class="container">
	{#if $navigating}
		<!-- I cba centering it horizontally -->
		<div style="text-align: center;">
			<span aria-busy="true" class="loading" />
		</div>
	{:else}
		<slot />
	{/if}
</main>
