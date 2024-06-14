import http from '$lib/httpUtil';
import type { PageLoad } from './$types';

export const load: PageLoad = async ({ params }) => {
	return {
		id: params.id,
        version: params.version,
		schemaInfo: (await http.get(`/api/application/${params.id}/schema/${params.version}`)).data.data
	};
};