<script lang="ts">
	import UuidCell from "../UUIDCell.svelte";

  export let user: any;
</script>

<article>
  <h3>User info</h3>
  <ul>
    <li>Username: {user.username}</li>
    <li><UuidCell prefix="ID: " id={user.id}></UuidCell></li>
    <li>Enabled: {user.enabled}</li>
  </ul>
</article>