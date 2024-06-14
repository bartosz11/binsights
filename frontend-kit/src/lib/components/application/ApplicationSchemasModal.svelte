<script lang="ts">
	import { closeModal } from 'svelte-modals';
	import SchemaRedirectButton from './SchemaRedirectButton.svelte';
	import http from '$lib/httpUtil';
	import SvelteTable from 'svelte-table';

	export let isOpen: boolean;
	export let id: string;

	const cols = [
		{
			key: 'version',
			title: 'Version',
			sortable: true,
			// @ts-ignore
			value: (v) => v.version
		},
		{
			key: 'redirbutton',
			title: 'Manage and view',
			sortable: false,
			renderComponent: SchemaRedirectButton
		}
	];

	async function fetchData() {
		const resp = await http.get(`/api/application/${id}/schemas`);
		return resp.data.data;
	}
</script>

{#if isOpen}
	<div role="dialog" class="modal">
		<div class="contents">
			{#await fetchData()}
			<span>Fetching schemas...</span>
			{:then data}
			<SvelteTable columns={cols} rows={data} />
			{/await}
			<button type="button" on:click={closeModal}>Close</button>
		</div>
	</div>
{/if}
