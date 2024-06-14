import http from '$lib/httpUtil';
import type { PageLoad } from './$types';

export const load: PageLoad = async () => {
	return (await http.get('/api/user')).data.data;
};
