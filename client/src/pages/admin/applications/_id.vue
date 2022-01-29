<template>
  <main role="main">
    <div class="jumbotron py-4">
      <div class="col-sm-12 mx-auto mt-1">
        <h1>Application #{{ $route.params.id }}</h1>
      </div>
    </div>

    <div v-if="account" class="row">
      <div class="col-6 text-right"><b>ID</b></div>
      <div class="col-6">{{ account.id }}</div>

      <div class="col-6 text-right"><b>Username</b></div>
      <div class="col-6">{{ account.username || '&lt;Empty&gt;' }}</div>

      <div class="col-6 text-right"><b>Bio</b></div>
      <div class="col-6">{{ account.bio || '&lt;Empty&gt;' }}</div>

      <div class="col-6 text-right"><b>Role</b></div>
      <div class="col-6">{{ account.role }}</div>

      <div class="col-6 text-right"><b>Status</b></div>
      <div class="col-6">{{ getStatusTitle(account.status) }}</div>

      <div v-if="account.status == 0" class="col-12 text-center mt-5">
        <button class="btn btn-success mr-5" @click="accept(true)">Activate</button>
        <button class="btn btn-danger" @click="accept(false)">Reject</button>
      </div>
    </div>
  </main>
</template>

<script>
import {ACCOUNT_STATUS} from '~/utils/constants';

export default {
  name: 'page-admin-applications-view',

  layout: 'account',

  fetch() {
    this.getAccount(this.$route.params.id);
  },

  data() {
    return {
      account: null
    };
  },

  methods: {
    getAccount(id) {
      return this.$repository.account.getOne(id)
          .then(response => this.account = response)
          .catch(error => console.log(error));
    },

    accept(decision) {
      this.$repository.account.accept(this.account.id, !!decision)
          .then(response => this.account = response)
          .catch(console.log);
    },

    getStatusTitle(status) {
      return ACCOUNT_STATUS[status];
    }
  }
};
</script>

