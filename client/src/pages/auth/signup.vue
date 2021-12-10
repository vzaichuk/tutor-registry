<template>
  <div class="card card-user">
    <div class="card-header">
      <h5 class="card-title">Registration</h5>
    </div>
    <div class="card-body">
      <div class="row">
        <div class="col">
          <div class="form-group">
            <label>Username</label>
            <input v-model="model.username" type="text" class="form-control" placeholder="Username" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <div class="form-group">
            <label>Email</label>
            <input v-model="model.email" type="email" class="form-control" placeholder="Email" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col">
          <div class="form-group">
            <label>Password</label>
            <input v-model="model.password" type="password" class="form-control" placeholder="Password" />
          </div>
        </div>
      </div>
      <div class="row">
        <div class="update ml-auto mr-auto">
          <button @click="signup" type="button" class="btn btn-primary btn-round">Sign up</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'page-auth-signup',

  layout: 'auth',

  auth: 'guest',

  data: function() {
    return {
      model: {
        email: '',
        password: '',
        username: ''
      },
      error: ''
    };
  },

  methods: {
    signup: function() {
      this.$axios.$post('/api/auth/signup', this.model);
    }
  }
}
</script>

<style>
  body {
    background-color: #f4f3ef;
  }
</style>
