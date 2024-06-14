<script>
	import { closeModal } from 'svelte-modals';

	export let onConfirm;
	export let isOpen;
	export let title;
</script>

{#if isOpen}
	<div role="dialog" class="modal">
		<div class="contents" style="min-width: 250px; padding: 1rem;">
			<h1 style="font-size: 1.25rem; font-weight: 700;">
				{title}
			</h1>
			<div role="group">
				<button class="no-border btn-blue" on:click={() => closeModal()}>No</button>
				<button class="no-border btn-red" on:click={() => onConfirm()}>Yes</button>
			</div>
		</div>
	</div>
{/if}
