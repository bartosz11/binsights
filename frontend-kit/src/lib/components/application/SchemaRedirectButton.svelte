<script lang="ts">
	import { goto } from "$app/navigation";
	import { closeModal } from "svelte-modals";
  // @ts-nocheck
  export let row: object;
  function onClick()  {
    goto(`/dashboard/applications/${row.application.id}/schema/${row.version}`);
    closeModal();
  }
</script>

<!-- dumb solution but works - center the button in parent <td> -->
<div style="width: full; text-align: center;">
  <button on:click={onClick}>View</button>
</div>
