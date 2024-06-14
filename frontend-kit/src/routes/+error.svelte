<script>
	import { page } from '$app/stores';
	import ErrorPage from '$lib/components/ErrorPage.svelte';
</script>

<ErrorPage error={$page.error}/>