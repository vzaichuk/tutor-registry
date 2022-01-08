const baseUrl = '/authentication';

export default $axios => ({
  signup(data) { return $axios.$post(`${baseUrl}/user/signup`, data); },
  setRole(role) { return $axios.$post(`${baseUrl}/user/role`, {role}); },
  refreshToken(token) {
    return $axios.$post(`${baseUrl}/login/refresh`, {refresh_token: token}, {
      headers: {Authorization: `Bearer ${token}`}
    });
  }
});
