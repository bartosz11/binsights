import http from '$lib/httpUtil';
import type { PageLoad } from './$types';


export const load: PageLoad = async ({ params }) => {
	return (await http.get(`/api/application/${params.id}`)).data.data;
};