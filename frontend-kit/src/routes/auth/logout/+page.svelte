<script lang="ts">
	import { goto } from '$app/navigation';
	import { deleteCookie } from '$lib/cookieUtil';
	import { onMount } from 'svelte';

	// This is not possible with +page.ts/js PageLoad function unless you disable prefetching on hover
	// If I left it in +page.ts then it'd just log out users when they hover over the log out button lmao
	onMount(() => {
		deleteCookie('auth-token');
		goto('/auth/login');
	});
</script>
