<script lang="ts">
	import { goto } from '$app/navigation';
	import http from '$lib/httpUtil';

	let username: string;
	let password: string;

	async function handleSubmit() {
		http
			.post('/api/auth/login', {
				username,
				password
			})
			.then((response) => {
				goto('/dashboard/applications');
			});
	}
</script>

<div class="grid-mine place-items-center h-screen">
	<div>
		<h2 style="text-align: center;">Sign in to binsights</h2>
		<form on:submit|preventDefault={handleSubmit} style="width: 400px;">
			<div>
				<label for="username">Username</label>
				<input name="username" placeholder="Username" type="text" bind:value={username} />
			</div>
			<div>
				<label for="password">Password</label>
				<input name="Password" placeholder="Password" type="password" bind:value={password} />
			</div>
			<button type="submit">Log in</button>
			<a href="/auth/register">Don't have an account yet?</a>
		</form>
	</div>
</div>
