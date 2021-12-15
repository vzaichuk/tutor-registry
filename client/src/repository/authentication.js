const baseUrl = '/authentication';

export default $axios => ({
  signup(data) { return $axios.$post(`${baseUrl}/user/signup`, data); }
});
