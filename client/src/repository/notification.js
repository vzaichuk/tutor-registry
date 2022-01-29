const baseUrl = '/notification';

export default $axios => ({
  getAll() { return $axios.$get(`${baseUrl}/all`); },
  getAllByUserId(id) { return $axios.$get(`${baseUrl}/all/${id}`); },
  getAmount() { return $axios.$get(`${baseUrl}/unseen-amount`); },
  getAmountByUserId(userId) { return $axios.$get(`${baseUrl}/unseen-amount/${userId}`); },
  seen(id) { return $axios.$put(`${baseUrl}/seen/${id}`); },
  seenByUserId(userId, id) { return $axios.$put(`${baseUrl}/seen/${userId}/${id}`); },
  remove(id) { return $axios.$delete(`${baseUrl}/remove/${id}`); },
  removeByUserId(userId, id) { return $axios.$delete(`${baseUrl}/remove/${userId}/${id}`); },
  removeAll() { return $axios.$delete(`${baseUrl}/remove-all`); },
  removeAllByUserId(userId) { return $axios.$delete(`${baseUrl}/remove-all/${userId}`); }
});
