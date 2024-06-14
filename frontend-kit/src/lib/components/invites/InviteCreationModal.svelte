<script lang="ts">
	import http from '$lib/httpUtil';
	import { error, promise } from '$lib/toastUtil';
	import { closeModal } from 'svelte-modals';
	import { number, required, useForm, validators } from 'svelte-use-form';

	export let isOpen: boolean;

	let expiryDate: number;

	function onSubmit() {
		promise(http.post(`/api/invite?expiryDate=${expiryDate}`), {
			success: 'Invite code created successfully.',
			error: null,
			loading: 'Creating an invite code...'
		})
			.then(() => location.reload())
			.catch((err) =>
				error(
					err.response?.data?.errors[0]?.message ??
						'Something went wrong while creating invite code.'
				)
			);
	}

	const form = useForm();
</script>

{#if isOpen}
	<div role="dialog" class="modal">
		<div class="contents" style="width: 400px;">
			<h3>Create a new invite</h3>
			<form on:submit|preventDefault={onSubmit} use:form>
				<div>
					<label for="expiry">Expiry date (unix timestamp)</label>
					<input
						name="expiry"
						placeholder="Expiry date"
						bind:value={expiryDate}
						use:validators={[required, number]}
						aria-invalid={$form.expiry?.touched
							? $form.expiry.valid
								? 'false'
								: 'true'
							: undefined}
						aria-describedby="expiry-hint"
					/>
					{#if $form.expiry?.touched && $form.expiry?.errors.required}
						<small id="expiry-hint">This field is required.</small>
					{/if}
					{#if $form.expiry?.touched && $form.expiry?.errors.number && !$form.expiry?.errors.required}
						<small id="expiry-hint">Given value is not a number.</small>
					{/if}
				</div>
				<div role="group">
					<button class="btn-red" type="button" on:click={closeModal}>Cancel</button>
					<button class="btn-green" type="submit">Create</button>
				</div>
			</form>
		</div>
	</div>
{/if}
