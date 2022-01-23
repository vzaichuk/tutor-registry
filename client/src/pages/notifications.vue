<template>
  <div class="main">
    <div class="jumbotron">
      <div class="col-sm-10 mx-auto">
        <h1>Notifications</h1>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'page-notifications',

  layout: 'account',

  fetch() {
    console.log('fetch');
  },

  methods: {
    getNotifications() {
      this.$repository.notification.getAll()
          .then(response => console.log(response))
          .catch(console.log);
    }
  }
}
</script>
