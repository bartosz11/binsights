<script>
	//@ts-nocheck
	import { goto } from '$app/navigation';
	import { openModal } from 'svelte-modals';
	import ApplicationRenameModal from './ApplicationRenameModal.svelte';
	import http from '$lib/httpUtil';
	import ApplicationSchemasModal from './ApplicationSchemasModal.svelte';
	import ConfirmationModal from '$lib/components/ConfirmationModal.svelte';
	import { IconPencil, IconTablePlus, IconTableShare, IconTrash } from '@tabler/icons-svelte';

	export let row;

	function deleteApplication() {
		openModal(ConfirmationModal, {
			title: `Are you sure you want to delete ${row.name}?`,
			onConfirm: () => {
				promise(http.delete(`/api/application/${row.id}`), {
					success: 'Application deleted successfully.',
					error: null,
					loading: 'Deleting application...'
				})
					.then(() => location.reload())
					.catch((err) =>
						error(
							err.response?.data?.errors[0]?.message ??
								'Something went wrong while deleting application.'
						)
					);
			}
		});
	}
</script>

<div>
	<button
		class="btn-green line-h-0"
		on:click={() => goto(`/dashboard/applications/${row.id}/new-schema`)}
		data-tooltip="Create new schema"
	>
		<IconTablePlus />
	</button>
	<button
		class="line-h-0"
		on:click={() => openModal(ApplicationSchemasModal, { id: row.id })}
		data-tooltip="View schemas"
	>
		<IconTableShare />
	</button>
	<button
		class="line-h-0"
		on:click={() => openModal(ApplicationRenameModal, { id: row.id, name: row.name })}
		data-tooltip="Rename"
	>
		<IconPencil />
	</button>
	<button class="btn-red line-h-0" on:click={deleteApplication} data-tooltip="Remove">
		<IconTrash />
	</button>
</div>
