<script lang="ts">
	import http from '$lib/httpUtil';
	import {error, promise } from '$lib/toastUtil';
	import {
		useForm,
		Hint,
		HintGroup,
		validators,
		minLength,
		pattern,
		required
	} from 'svelte-use-form';
	import type { Validator } from 'svelte-use-form';
	let oldPassword: string;
	let newPassword: string;
	let confirmNewPassword: string;
	function onSubmit() {
		promise(http
			.patch('/api/user/password', { oldPassword, newPassword, confirmNewPassword }), {
				success: "Password changed successfully.",
				error: null,
				loading: "Changing password..."
			})
			.then(() => location.reload())
			.catch((err) =>
          error(
            err.response?.data?.errors[0]?.message ??
              "Something went wrong while changing your password."
          )
		);
	}

	const passwordMatch: Validator = (value, form) => {
		return value === form.newpassword?.value
			? null
			: { passwordMatch: 'Passwords do not match' };
	};

	const form = useForm();
</script>

<article>
	<h3>Change password</h3>
	<form use:form on:submit|preventDefault={onSubmit}>
		<div>
			<label for="oldpassword">Old password</label>
			<input
				type="password"
				name="oldpassword"
				placeholder="Old password"
				bind:value={oldPassword}
				use:validators={[required]}
				aria-invalid={$form.oldpassword?.touched ? $form.oldpassword.valid ? "false" : "true" : undefined}
				aria-describedby="oldpassword-hint"
			/>
			{#if $form.oldpassword?.touched && $form.oldpassword?.errors.required}
				<small id="oldpassword-hint">This field is required.</small>
			{/if}
		</div>
		<div>
			<label for="newpassword">New password</label>
			<input
				type="password"
				name="newpassword"
				placeholder="New password"
				bind:value={newPassword}
				use:validators={[required, pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$')]}
				aria-invalid={$form.newpassword?.touched ? $form.newpassword.valid ? "false" : "true" : undefined}
				aria-describedby="newpassword-hint"
			/>
			{#if $form.newpassword?.touched && $form.newpassword?.errors.required}
				<small id="newpassword-hint">This field is required.</small>
			{/if}
			<!-- Display this only when the field was touched, the pattern validation fails but the "required" validation passes - hints can't really stack up in PicoCSS  -->
			{#if $form.newpassword?.touched && ($form.newpassword?.errors.pattern && !$form.newpassword?.errors.required)}
				<small id="newpassword-hint">New password must be at least 8 characters long, contain 1 lowercase letter, 1 uppercase letter, 1 digit and 1 special character.</small>
			{/if}
		</div>
		<div>
			<label for="confirmnewpassword">Confirm new password</label>
			<input
				type="password"
				name="confirmnewpassword"
				placeholder="Confirm new password"
				bind:value={confirmNewPassword}
				use:validators={[required, passwordMatch]}
				aria-invalid={$form.confirmnewpassword?.touched ? $form.confirmnewpassword.valid ? "false" : "true" : undefined}
				aria-describedby="confirmnewpassword-hint"
			/>
			{#if $form.confirmnewpassword?.touched && $form.confirmnewpassword?.errors.required}
				<small id="confirmnewpassword-hint">This field is required.</small>
			{/if}
			{#if $form.confirmnewpassword?.touched && $form.confirmnewpassword?.errors.passwordMatch}
				<small id="confirmnewpassword-hint">Passwords do not match.</small>
			{/if}
		</div>
		<button>Change password</button>
	</form>
</article>
