<script lang="ts">
	import http from '$lib/httpUtil';
	import { error, promise } from '$lib/toastUtil';
	import { closeModal } from 'svelte-modals';
	import { required, useForm, validators, type Validator } from 'svelte-use-form';

	export let isOpen: boolean;

	let expiryDate: string;

	function onSubmit() {
		const expiryEpoch: number = new Date(expiryDate).getTime() / 1000;
		console.log(expiryEpoch);
		promise(http.post(`/api/invite?expiryDate=${expiryEpoch}`), {
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
	
	const isFuture: Validator = (value) => {
		// Don't call me a frontend developer - I cannot comprehend JS's stupidity and the fact that the comparison below works
		return new Date(value) > new Date() ? null : { isFuture: 'Given date must be in the future' };
	};
</script>

{#if isOpen}
	<div role="dialog" class="modal">
		<div class="contents" style="width: 400px;">
			<h3>Create a new invite</h3>
			<form on:submit|preventDefault={onSubmit} use:form>
				<div>
					<label for="expiry">Expiry date</label>
					<input
						name="expiry"
						type="datetime-local"
						placeholder="Expiry date"
						bind:value={expiryDate}
						use:validators={[required, isFuture]}
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
					{#if $form.expiry?.touched && $form.expiry?.errors.isFuture && !$form.expiry?.errors.required}
						<small id="expiry-hint">Given value must be in the future.</small>
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
