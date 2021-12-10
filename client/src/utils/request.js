import {convertFromJson} from 'fiql-query-builder';

export const composeRequestParams = payload => {
  const params = {};

  if (payload.pagination) {
    params.page = payload.pagination.page;
    params.size = payload.pagination.size;
    if (payload.pagination.sort) {
      params.sort = payload.pagination.sort;
    }
  }

  if (payload.filter && (typeof (payload.filter)) === 'object') {
    params.filter = convertFromJson(payload.filter);
  }

  return params;
};
