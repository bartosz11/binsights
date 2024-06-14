import http from '$lib/httpUtil';
import type { PageLoad } from './$types';

export const load: PageLoad = async () => {
    return {
        apps: (await http.get('/api/application')).data.data
    };
};
