<script lang="ts">
	import { goto } from '$app/navigation';
	import http from '$lib/httpUtil';
	import { pattern, required, useForm, validators } from 'svelte-use-form';

	let username: string;
	let password: string;
	let inviteCode: string;

	async function handleSubmit() {
		http
			.post(`/api/auth/register${inviteCode !== undefined ? '?invite=' + inviteCode : ''}`, {
				username,
				password
			})
			.then((response) => {
				goto('/auth/login');
			});
	}

	const form = useForm();
</script>

<div class="grid-mine place-items-center h-screen">
	<div>
		<h2 style="text-align: center;">Sign up to binsights</h2>
		<form on:submit|preventDefault={handleSubmit} style="width: 400px;" use:form>
			<div>
				<label for="username">Username</label>
				<input
					name="username"
					placeholder="Username"
					type="text"
					bind:value={username}
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
			<div>
				<label for="password">Password</label>
				<input
					name="password"
					placeholder="Password"
					type="password"
					bind:value={password}
					use:validators={[
						required,
						pattern('^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$')
					]}
					aria-invalid={$form.password?.touched
						? $form.password.valid
							? 'false'
							: 'true'
						: undefined}
					aria-describedby="password-hint"
				/>
				{#if $form.password?.touched}
					{#if $form.password?.errors.required}
						<small id="password-hint">This field is required.</small>
					{/if}
					{#if $form.password?.errors.pattern && !$form.password?.errors.required}
						<small id="password-hint"
							>Password must be at least 8 characters long, contain 1 lowercase letter, 1 uppercase
							letter, 1 digit and 1 special character.</small
						>
					{/if}
				{/if}
			</div>
			<div>
				<label for="inviteCode">Invite code</label>
				<!-- We don't really have a way to validate if there are any users existing to tell if the inv code is required or not  -->
				<input
					name="inviteCode"
					placeholder="Invite code (optional for the first user)"
					type="password"
					bind:value={inviteCode}
				/>
			</div>
			<button type="submit">Sign up</button>
			<a href="/auth/login">Have an account already?</a>
		</form>
	</div>
</div>
