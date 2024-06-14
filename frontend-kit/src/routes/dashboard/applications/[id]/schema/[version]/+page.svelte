<script lang="ts">
	import UuidCell from '$lib/components/UUIDCell.svelte';
	import http from '$lib/httpUtil';
	import SvelteTable from 'svelte-table';
	import type { PageData } from './$types';
	import { goto } from '$app/navigation';
	import { closeModal, openModal } from 'svelte-modals';
	import ConfirmationModal from '$lib/components/ConfirmationModal.svelte';

	export let data: PageData;
	let enabled: boolean = data.schemaInfo.enabled;

	const cols = [
		{
			key: 'name',
			title: 'Name',
			sortable: true,
			// @ts-ignore
			value: (v) => v.name
		},
		{
			key: 'description',
			title: 'Description',
			// @ts-ignore
			value: (v) => v.description,
			sortable: true
		},
		{
			key: 'type',
			title: 'Type',
			// @ts-ignore
			value: (v) => v.type.substring(0, 1).toUpperCase() + v.type.substring(1).toLowerCase(),
			sortable: true
		}
	];

	function toggleSchema() {
		http
			.patch(`/api/schema/${data.schemaInfo.id}/status?enable=${enabled}`)
			.then((resp) => location.reload());
	}

	function onDelete() {
		openModal(ConfirmationModal, {
			title: `Are you sure you want to delete schema ${data.schemaInfo.version}?`,
			onConfirm: () => {
				http.delete(`/api/schema/${data.schemaInfo.id}`).then(() => {
					goto('/dashboard/applications');
					closeModal();
				});
			}
		});
	}
</script>

<section class="header-with-button">
	<h1>Schema version {data.schemaInfo.version} for {data.schemaInfo.application.name}</h1>
	<button class="btn-red" on:click={onDelete}>Delete</button>
</section>
<section>
	<UuidCell prefix="ID: " id={data.schemaInfo.id} />
	<p>Collects IP addresses: {data.schemaInfo.collectIPAddresses ? 'yes' : 'no'}</p>
	<label for="enabled">
		<input type="checkbox" name="enabled" bind:checked={enabled} on:change={toggleSchema} />
		Enabled
	</label>
	<h4>Fields</h4>
	<SvelteTable columns={cols} rows={data.schemaInfo.fields} />
</section>
