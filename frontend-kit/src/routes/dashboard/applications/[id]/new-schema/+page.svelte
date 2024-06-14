<script lang="ts">
	import { goto } from '$app/navigation';
	import FieldFormRow from '$lib/components/schema/FieldFormRow.svelte';
	import http from '$lib/httpUtil';
	import { required, useForm, validators } from 'svelte-use-form';
	import type { PageData } from './$types';
	import { fieldFormRows } from '$lib/stores';
	//@ts-nocheck
	export let data: PageData;

	let version: string;
	let enabled: boolean = false;
	let collectIPAddresses: boolean = false;

	// Function to add a new component instance
	function addComponent() {
		//@ts-ignore
		$fieldFormRows = [...$fieldFormRows, { name: '', description: '', type: '' }];
	}
	// Removing the FieldFormRows is handled in them

	function onSubmit() {
		http.post(`/api/application/${data.id}/schema`, {
			version,
			enabled,
			collectIPAddresses,
			metricFields: $fieldFormRows
		}).then(resp => { 
			const respData = resp.data.data;
			goto(`/dashboard/applications/${respData.application.id}/schema/${respData.version}`)
			fieldFormRows.set([{ name: '', description: '', type: '' }]);
		});
	}

	const form = useForm({}, "new-schema-form");
</script>

<section>
	<h1>Create a new schema for {data.name}</h1>
</section>

<form on:submit|preventDefault={onSubmit} use:form>
	<div>
		<label for="version">Version</label>
		<input
			name="version"
			placeholder="Version"
			bind:value={version}
			use:validators={[required]}
			aria-invalid={$form.version?.touched ? ($form.version.valid ? 'false' : 'true') : undefined}
			aria-describedby="version-hint"
		/>
		{#if $form.version?.touched && $form.version?.errors.required}
			<small id="version-hint">This field is required.</small>
		{/if}
	</div>
	<!-- No validations for checkbox because they always have a default value of	 false -->
	<div>
		<label for="enabled">
			<input name="enabled" type="checkbox" bind:checked={enabled} />
			Enabled
		</label>
	</div>
	<div style="margin-bottom: 24px;">
		<label for="collectips">
			<input name="collectips" type="checkbox" bind:checked={collectIPAddresses} />
			Collect IP addresses (x_ip_address)
		</label>
	</div>
	<h3>Fields</h3>
	{#each $fieldFormRows as component, index}
		<FieldFormRow
			bind:name={component.name}
			bind:description={component.description}
			bind:type={component.type}
			{index}
			{form}
		/>
	{/each}
	<button type="button" on:click={addComponent}>Add next field</button>
	<div role="group">
		<button class="btn-red" type="button" on:click={() => goto('/dashboard/applications')}
			>Cancel</button
		>
		<button class="btn-green" type="submit">Create</button>
	</div>
</form>
