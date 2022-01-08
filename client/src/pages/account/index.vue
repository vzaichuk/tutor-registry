<template>
  <main role="main">
    <div class="jumbotron">
      <div class="col-sm-10 mx-auto">
        <h1>{{ username }}</h1>
        <p class="mt-5">{{ bio }}</p>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: 'page-account',

  layout: 'account',

  computed: {
    username() {
      return this.$auth.user.username || 'user' + this.$auth.user.id;
    },

    bio() {
      return this.$auth.user.bio || '<bio is empty>';
    }
  }
};
</script>
