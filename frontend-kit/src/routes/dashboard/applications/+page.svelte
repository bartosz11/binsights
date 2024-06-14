<script lang="ts">
	import UuidCell from '$lib/components/UUIDCell.svelte';
	import SvelteTable from 'svelte-table';
	import ApplicationActionsCell from '$lib/components/application/ApplicationActionsCell.svelte';
	import { openModal } from 'svelte-modals';
	import ApplicationCreateModal from '$lib/components/application/ApplicationCreateModal.svelte';
	import type { PageData } from './$types';

	export let data: PageData;
	// @ts-ignore
	const cols = [
		{
			key: 'id',
			title: 'ID',
			sortable: false,
			renderComponent: UuidCell
		},
		{
			key: 'name',
			title: 'Name',
			// @ts-ignore
			value: (v) => v.name,
			sortable: true
		},
		{
			key: 'influxDBBucketName',
			title: 'InfluxDB bucket name',
			// @ts-ignore
			value: (v) => v.influxDBBucketName,
			sortable: true
		},
		{
			key: 'actions',
			title: 'Actions',
			sortable: false,
			renderComponent: ApplicationActionsCell
		}
	];
</script>

<section>
	<div class="header-with-button">
		<h1>Applications</h1>
		<button class="btn-green" on:click={() => openModal(ApplicationCreateModal)}>Create new</button>
	</div>
</section>

<section>
	<SvelteTable columns={cols} rows={data.apps} />
</section>
