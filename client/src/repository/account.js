const baseUrl = '/account';

export default $axios => ({
  getAllOnReview(params) { return $axios.$get(`${baseUrl}/account/review`, {params}); },
  getOne(id) { return $axios.$get(`${baseUrl}/account/${id}`); },
  accept(id, decision) { return $axios.$put(`${baseUrl}/account/accept`, {id, decision}); },
  getAllTutors(params) { return $axios.$get(`${baseUrl}/tutor/all`, {params}); },
  getMyTutors: () => $axios.$get(`${baseUrl}/tutor/my`),
  assign: id => $axios.$post(`${baseUrl}/account/assign/${id}`)
});
