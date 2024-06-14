<script lang="ts">
	import { fieldFormRows } from '$lib/stores';
	import { required, validators, type Validator, type Form, Hint } from 'svelte-use-form';

	export let name: string;
	export let description: string;
	export let type: string;
	export let index: number;
	//@ts-ignore
	export let form: Form;

	const notPlaceholder: Validator = (value) => {
		return value !== '' ? null : { notPlaceholder: 'This field is required' };
	};

	function removeOwnRow() {
		fieldFormRows.set($fieldFormRows.filter((_, i) => i !== index));
	}
</script>

<div role="group">
	<input
		placeholder="Name"
		bind:value={name}
		name={`name${index}`}
		use:validators={[required]}
		aria-invalid={$form[`name${index}`]?.touched
			? $form[`name${index}`].valid
				? 'false'
				: 'true'
			: undefined}
	/>

	<input placeholder="Description" name={`description${index}`} bind:value={description} />
	<select
		bind:value={type}
		name={`type${index}`}
		use:validators={[notPlaceholder]}
		aria-invalid={$form[`type${index}`]?.touched
			? $form[`type${index}`]?.valid
				? 'false'
				: 'true'
			: undefined}
	>
		<!-- placeholder -->
		<option value="" selected disabled>Type</option>
		<option value="STRING">String</option>
		<option value="BOOLEAN">Boolean</option>
		<option value="DOUBLE">Double (decimal)</option>
		<option value="LONG">Long (integer)</option>
	</select>
	<button type="button" on:click={() => removeOwnRow()}>Remove</button>
</div>

<div>
	<Hint class="form-hint" on="required" form="new-schema-form" for={`name${index}`}>Name must be specified.</Hint>
	<Hint class="form-hint" on="notPlaceholder" form="new-schema-form" for={`type${index}`}>Type must be specified.</Hint>
</div>
