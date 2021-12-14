const AUTH_BEARER_POPULATOR = function(data, headers) {
  let obj = JSON.parse(data);
  headers.post['Authorization'] = 'Bearer ' + obj.refresh_token;
  return '';
};

export default async ({ $axios }) => {
  $axios.interceptors.request.use(
    config => {
      if (config.url.endsWith('authentication/login/refresh')) {
        config.transformRequest.push(AUTH_BEARER_POPULATOR);
      }
      return config;
    },
    error => Promise.reject(error)
  );
};
