<template>
  <main role="main">
    <div class="jumbotron py-4">
      <div class="col-sm-12 mx-auto mt-1">
        <h1>Applications</h1>
      </div>
    </div>

    <table v-if="accounts && accounts.length" class="table table-striped">
      <thead>
        <tr>
          <th>ID</th>
          <th>Username</th>
          <th>Role</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="account in accounts" :key="account.id">
          <td>{{ account.id }}</td>
          <td>{{ account.username }}</td>
          <td>{{ account.role }}</td>
          <td class="text-right">
            <NuxtLink class="btn btn-sm btn-primary" :to="`/admin/applications/${account.id}`">Open</NuxtLink>
          </td>
        </tr>
      </tbody>
    </table>
    <h2 v-else class="text-center">No application is found</h2>
  </main>
</template>

<script>
export default {
  name: 'page-admin-applications',

  layout: 'account',

  fetch() {
    this.getAccounts();
  },

  data() {
    return {
      accounts: []
    };
  },

  methods: {
    getAccounts() {
      return this.$repository.account.getAllOnReview({})
          .then(response => this.accounts = response.content)
          .catch(error => console.log(error));
    }
  }
};
</script>

