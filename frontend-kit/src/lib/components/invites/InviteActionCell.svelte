<script lang="ts">
	import http from '$lib/httpUtil';
	import { openModal } from 'svelte-modals';
	import ConfirmationModal from '$lib/components/ConfirmationModal.svelte';
	import { IconTrash } from '@tabler/icons-svelte';
	import { error, promise } from '$lib/toastUtil';
	export let row: object;

	function deleteInvite() {
		openModal(ConfirmationModal, {
			title: `Are you sure you want to delete ${row.id}?`,
			onConfirm: () => {
				promise(
					http.delete(`/api/invite/${row.id}`),
					{
						success: 'Invite deleted successfully.',
						error: null,
						loading: 'Deleting invite...'
					}
				)
					.then(() => location.reload())
					.catch((err) =>
						error(
							err.response?.data?.errors[0]?.message ??
								'Something went wrong while deleting invite.'
						)
					);
			}
		});
	}
</script>

<div>
	<!-- I think the SVG inside the icon inherits a line height so we have to set it to 0  -->
	<button class="btn-red line-h-0" data-tooltip="Delete" on:click={deleteInvite}>
		<IconTrash size={24} />
	</button>
</div>
