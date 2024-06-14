import http from '$lib/httpUtil';
import type { PageLoad } from './$types';

export const load: PageLoad = async () => {
	return {
		invites: (await http.get('/api/invite')).data.data
	};
};
