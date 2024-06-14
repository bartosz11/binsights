<script lang="ts">
	import '../app.scss';

	import { goto } from '$app/navigation';
	import http from '$lib/httpUtil';
	import { Modals, closeModal } from 'svelte-modals';
	import { onMount } from 'svelte';
	import { page } from '$app/stores';
	import { Toaster } from 'svelte-french-toast';
	import { deleteCookie } from '$lib/cookieUtil';
	import ErrorPage from '$lib/components/ErrorPage.svelte';
	import { success } from '$lib/toastUtil';

	const checkAuthentication = async () => {
		try {
			// We don't need anything from this request, we just want it to not fail
			await http.get('/api/user');
			const pathname = $page.url.pathname.toLowerCase();
			if (pathname === '/auth/login' || pathname === '/auth/register') {
				success("You're already logged in, you'll get redirected to dashboard soon.");
				goto('/dashboard/applications');
			}
		} catch (error) {
			if (error.response?.status === 403) {
				deleteCookie('auth-token');
				goto('/auth/login');
			} else {
				throw new Error('An unexpected error occurred while checking validity of your session.');
			}
		}
	};

</script>

<!-- svelte-ignore a11y-no-static-element-interactions -->
<!-- svelte-ignore a11y-click-events-have-key-events -->
<!-- We don't need this to be conditional  -->
<Modals>
	<div slot="backdrop" class="backdrop" on:click={closeModal} />
</Modals>
<Toaster />
<svelte:head>
	<title>binsights</title>
</svelte:head>

{#await checkAuthentication() then x}
	<slot />
{:catch error}
	<ErrorPage {error} />
{/await}
