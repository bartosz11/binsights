<script lang="ts">
	import ChangePasswordCard from '$lib/components/account/ChangePasswordCard.svelte';
	import ChangeUsernameCard from '$lib/components/account/ChangeUsernameCard.svelte';
	import DeleteUserCard from '$lib/components/account/DeleteUserCard.svelte';
	import UserInfoCard from '$lib/components/account/UserInfoCard.svelte';
	import type { PageData } from './$types';

	export let data: PageData;
</script>

<section>
	<h1>Account</h1>
</section>
<section>
	<UserInfoCard user={data} />
	<ChangeUsernameCard />
	<ChangePasswordCard />
	<DeleteUserCard />
</section>
