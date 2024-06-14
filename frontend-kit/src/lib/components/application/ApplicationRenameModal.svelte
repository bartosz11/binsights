<script lang="ts">
	import http from '$lib/httpUtil';
	import { error, promise } from '$lib/toastUtil';
	import { closeModal } from 'svelte-modals';
	import { number, required, useForm, validators } from 'svelte-use-form';

	export let isOpen: boolean;

	export let id: string;
	export let name: string;
	let newName: string;

	function onSubmit() {
		promise(http.patch(`/api/application/${id}`, { name: newName }), {
				success: "Application renamed successfully.",
				error: null,
				loading: "Renaming application..."
			})
			.then(() => location.reload())
			.catch((err) =>
          error(
            err.response?.data?.errors[0]?.message ??
              "Something went wrong while renaming application."
          )
		);
	}

	const form = useForm();
</script>

{#if isOpen}
	<div role="dialog" class="modal">
		<div class="contents" style="min-width: 400px;">
			<h3>Rename application {name}</h3>
			<form on:submit|preventDefault={onSubmit} use:form>
				<div>
					<label for="newname">New name</label>
					<input
						placeholder="New name"
						name="newname"
						bind:value={newName}
						use:validators={[required]}
						aria-invalid={$form.newname?.touched
							? $form.newname.valid
								? 'false'
								: 'true'
							: undefined}
						aria-describedby="newname-hint"
					/>
					{#if $form.newname?.touched && $form.newname?.errors.required}
						<small id="newname-hint">This field is required.</small>
					{/if}
				</div>
				<div role="group">
					<button class="btn-red" type="button" on:click={closeModal}>Cancel</button>
					<button type="submit" class="btn-green">Rename</button>
				</div>
			</form>
		</div>
	</div>
{/if}
