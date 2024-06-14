<script lang="ts">
	import http from '$lib/httpUtil';
	import { error, promise } from '$lib/toastUtil';
	import { required, useForm, validators } from 'svelte-use-form';

	let name: string;

	function onSubmit() {
		promise(http.patch('/api/user/username', { name }).then((resp) => location.reload()), {
				success: "Username changed successfully.",
				error: null,
				loading: "Changing username..."
			})
			.then(() => location.reload())
			.catch((err) =>
          error(
            err.response?.data?.errors[0]?.message ??
              "Something went wrong while changing your username."
          )
		);
	}

	const form = useForm();
</script>

<article>
	<h3>Change username</h3>
	<form on:submit|preventDefault={onSubmit} use:form>
		<div>
			<label for="username">New username</label>
			<input
				name="username"
				placeholder="New username"
				bind:value={name}
				use:validators={[required]}
				aria-invalid={$form.username?.touched
					? $form.username.valid
						? 'false'
						: 'true'
					: undefined}
				aria-describedby="username-hint"
			/>
			{#if $form.username?.touched && $form.username?.errors.required}
				<small id="username-hint">This field is required.</small>
			{/if}
		</div>
		<button>Change username</button>
	</form>
</article>
