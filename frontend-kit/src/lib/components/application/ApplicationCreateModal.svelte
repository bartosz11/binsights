<script lang="ts">
	import http from '$lib/httpUtil';
	import { error, promise } from '$lib/toastUtil';
	import { closeModal } from 'svelte-modals';
	import { number, required, useForm, validators, type Validator } from 'svelte-use-form';

	export let isOpen: boolean;

	let name: string;
	let bucketName: string;
	let retention: number;

	function onSubmit() {
		promise(http
			.post('/api/application', { name, bucketName, retention }), {
				success: "Application created successfully.",
				error: null,
				loading: "Creating application..."
			})
			.then(() => location.reload())
			.catch((err) =>
          error(
            err.response?.data?.errors[0]?.message ??
              "Something went wrong while creating application."
          )
		);
	}
	const form = useForm();

	const minZero: Validator = (value) => {
		return Number(value) >= 0 ? null : { minZero: 'Given value is lower than 0' };
	};
</script>

{#if isOpen}
	<div role="dialog" class="modal">
		<div class="contents" style="width: 420px;">
			<h3>Create a new application</h3>
			<form on:submit|preventDefault={onSubmit} use:form>
				<div>
					<label for="name">Name</label>
					<input
						placeholder="Name"
						name="name"
						bind:value={name}
						use:validators={[required]}
						aria-invalid={$form.name?.touched ? ($form.name.valid ? 'false' : 'true') : undefined}
						aria-describedby="name-hint"
					/>
					{#if $form.name?.touched && $form.name?.errors.required}
						<small id="name-hint">This field is required.</small>
					{/if}
				</div>
				<div>
					<label for="influxbucketname">InfluxDB bucket name</label>
					<input
						placeholder="Bucket name (optional)"
						name="influxbucketname"
						bind:value={bucketName}
					/>
				</div>
				<div>
					<label for="retention">InfluxDB bucket retention</label>
					<input
						placeholder="Bucket retention (seconds)"
						name="retention"
						bind:value={retention}
						use:validators={[required, minZero]}
						aria-invalid={$form.retention?.touched
							? $form.retention.valid
								? 'false'
								: 'true'
							: undefined}
						aria-describedby="retention-hint"
					/>
					{#if $form.retention?.touched}
						{#if $form.retention?.errors.required}
							<small id="retention-hint">This field is required.</small>
						{/if}
						<!-- I just decided it'll be easier to give the same hint to the user when value isn't a number/is lower than 0 -->
						{#if $form.retention?.errors.minZero && !$form.retention?.errors.required}
							<small id="retention-hint">Provided value must be a number higher or equal to 0.</small>
						{/if}
					{/if}
				</div>
				<div role="group">
					<button class="btn-red" type="button" on:click={closeModal}>Cancel</button>
					<button class="btn-green" type="submit">Create</button>
				</div>
			</form>
		</div>
	</div>
{/if}
