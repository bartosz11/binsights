<!-- This component is used by the +error files and the +layout auth check error -->
<script lang="ts">
	import { IconAlertTriangleFilled } from '@tabler/icons-svelte';

	export let error: Error | null;
</script>

<div class="error-page">
	<h1>
		<span class="pico-text-red"><IconAlertTriangleFilled /></span>
		Something went wrong
	</h1>
	{#if error?.message}
	<p>{error.message}</p>
	{/if}
</div>

<style>
	.error-page {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		height: 100vh;
		text-align: center;
	}
</style>
