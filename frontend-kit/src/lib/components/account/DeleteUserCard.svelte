<script lang="ts">
	import { goto } from '$app/navigation';
	import { deleteCookie } from '$lib/cookieUtil';
	import http from '$lib/httpUtil';
	import { closeModal, openModal } from 'svelte-modals';
	import ConfirmationModal from '$lib/components/ConfirmationModal.svelte';
	import { error, promise } from '$lib/toastUtil';

	function deleteUser() {
		openModal(ConfirmationModal, {
			title: 'Are you sure you want to delete your account?',
			onConfirm: () => {
				promise(http.delete('/api/user'), {
					success: 'Account deleted successfully.',
					error: null,
					loading: 'Deleting your account...'
				})
					.then(() => {
						deleteCookie('auth-token');
						goto('/auth/login');
						closeModal();
					})
					.catch((err) =>
						error(
							err.response?.data?.errors[0]?.message ??
								'Something went wrong while deleting your account.'
						)
					);
			}
		});
	}
</script>

<article>
	<h3>Delete account</h3>
	<button class="btn-red" on:click={deleteUser}>Delete account</button>
</article>
