import { writable } from "svelte/store";

export const fieldFormRows = writable([{ name: '', description: '', type: '' }]);
