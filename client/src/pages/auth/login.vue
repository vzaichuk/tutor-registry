<template>
  <div class="wrapper text-center">
    <form class="form-signin">
      <img class="mb-4 invert" src="/img/hero-logo.png" alt="" width="72" height="72" />

      <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
      
      <label for="inputEmail" class="sr-only">Email address</label>
      <input v-model="email" type="email" class="form-control" placeholder="Email address" @keypress.enter="login" required="" autofocus="" />
      
      <label for="inputPassword" class="sr-only">Password</label>
      <input v-model="password" type="password" class="form-control" placeholder="Password" @keypress.enter="login" required="" />

      <button @click="login" type="button" class="btn btn-lg btn-primary btn-block">Log in</button>
      <div class="mt-2 text-left">
        <NuxtLink to="/auth/signup" class="text-dark mt-4">Registration</NuxtLink>
      </div>
      
      <p class="mt-5 mb-3 text-muted">© 2021</p>
    </form>
  </div>
</template>

<script>
import errorHanlderMixin from '~/components/mixins/error-handler';

export default {
  name: 'page-auth-login',

  layout: 'auth',

  auth: 'guest',

  mixins: [errorHanlderMixin],

  data: function() {
    return {
      email: '',
      password: '',
      error: ''
    };
  },

  methods: {
    login: function() {
      this.$auth
          .loginWith('local', {auth: {username: this.email, password: this.password}})
          .catch(this.handleError)
          .then(() => this.$router.push('/account'));
    }
  }
}
</script>

<style scoped>
  .wrapper {
    height: 100vh;
  }

  .wrapper {
    display: -ms-flexbox;
    display: -webkit-box;
    display: flex;
    -ms-flex-align: center;
    -ms-flex-pack: center;
    -webkit-box-align: center;
    align-items: center;
    -webkit-box-pack: center;
    justify-content: center;
    padding-top: 40px;
    padding-bottom: 40px;
    background-color: #f5f5f5;
  }

  .form-signin {
    width: 330px;
    max-width: 330px;
    padding: 15px;
    margin: 0 auto;
  }
  .form-signin .checkbox {
    font-weight: 400;
  }
  .form-signin .form-control {
    position: relative;
    box-sizing: border-box;
    height: auto;
    padding: 10px;
    font-size: 16px;
  }
  .form-signin .form-control:focus {
    z-index: 2;
  }
  .form-signin input[type="email"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  }
  .invert { filter: invert(100%); }
</style>
