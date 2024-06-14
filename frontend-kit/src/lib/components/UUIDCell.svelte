<script lang="ts">
	import { onMount } from 'svelte';
	//makes it more usable for tables, i can just supply an object though
	export let row: any = null;
	export let prefix: string = "";
	export let id: string = "";

	let uuid: string;
	let displayedId: string;

	onMount(() => {
		// @ts-ignore
		uuid = row?.id ?? id;
		displayedId = uuid.slice(0, uuid.indexOf('-')) + '...';
	});

	function onClick() {
		navigator.clipboard.writeText(uuid);
	}
</script>

<span>
	{prefix} <button data-tooltip="Click to copy" class="noborder" on:click={onClick}>{displayedId}</button>
</span>

<style>
	.noborder {
		padding: 0;
		border: none;
		background: none;
	}
</style>
