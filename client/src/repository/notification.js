const baseUrl = '/notification';

export default $axios => ({
  getAll() { return $axios.$get(`${baseUrl}/all`); },
  getAmount() { return $axios.$get(`${baseUrl}/amount`); }
});
