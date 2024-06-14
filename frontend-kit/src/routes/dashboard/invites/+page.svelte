<script lang="ts">
	import InviteActionCell from '$lib/components/invites/InviteActionCell.svelte';
	import InviteCreationModal from '$lib/components/invites/InviteCreationModal.svelte';
	import { openModal } from 'svelte-modals';
	import SvelteTable from 'svelte-table';
	import type { PageData } from './$types';

	export let data: PageData;
	const cols = [
		{
			key: 'code',
			title: 'Code',
			sortable: true,
			value: (v) => v.id
		},
		{
			key: 'expiresOn',
			title: 'Expires on',
			sortable: true,
			value: (v) => {
				return new Date(v.expiresOn * 1000).toLocaleString();
			}
		},
		{
			key: 'actions',
			title: 'Actions',
			sortable: false,
			renderComponent: InviteActionCell
		}
	];
</script>

<div class="header-with-button">
	<h1>Invite codes</h1>
	<button class="btn-green" on:click={() => openModal(InviteCreationModal)}>Create new</button>
</div>
<SvelteTable columns={cols} rows={data.invites} />
