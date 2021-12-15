<template>
  <div class="wrapper text-center">
    <form class="form-signin">
      <img class="mb-4 invert" src="/img/hero-logo.png" alt="" width="72" height="72" />

      <h1 class="h3 mb-3 font-weight-normal">Sign up</h1>
      
      <label for="inputEmail" class="sr-only">Email address</label>
      <input v-model="model.email" type="email" class="form-control" placeholder="Email address" @keypress.enter="signup" required="" autofocus="" />
      <div v-if="errors.email" class="has-error text-left mb-2 text-danger">{{ errors.email }}</div>
      
      <label for="inputPassword" class="sr-only">Password</label>
      <input v-model="model.password" type="password" class="form-control" placeholder="Password" @keypress.enter="signup" required="" />
      <div v-if="errors.password" class="has-error text-left mb-2 text-danger">{{ errors.password }}</div>

      <label for="" class="sr-only">Repeat password</label>
      <input v-model="model.passwordRepeat" type="password" class="form-control mb-3" placeholder="Repeat password" @keypress.enter="signup" required="" />

      <button @click="signup" type="button" class="btn btn-lg btn-primary btn-block">Sign up</button>
      <div class="mt-2 text-left">
        <NuxtLink to="/auth/login" class="text-dark mt-4">Log in</NuxtLink>
      </div>
      
      <p class="mt-5 mb-3 text-muted">© 2021</p>
    </form>
    <div v-if="false">
      <img class="mb-4" src="/img/matrix.jpg" style="max-width:400px;" />
      <div>
        <h4 class="mb-4">Now, choose your side wisely</h4>
        <button class="btn btn-danger px-5 mr-5">Student</button>
        <button class="btn btn-primary px-5">Tutor</button>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'page-auth-signup',

  layout: 'auth',

  auth: 'guest',

  data() {
    return {
      model: {
        email: '',
        password: '',
        passwordRepeat: ''
      },
      errors: {
        email: '',
        password: ''
      }
    };
  },

  methods: {
    signup() {
      this.cleanErrors();
      this.$repository.authentication.signup(this.model)
          .then(console.log)
          .catch(error => this.$errorHandler.handle(this, error, this.populateErrors));
    },

    populateErrors(errors) {
      Object.entries(errors)
          .forEach(([k, v]) => this.errors[k] = v.charAt(0).toUpperCase() + v.slice(1));
    },

    cleanErrors() {
      Object.keys(this.errors).forEach(k => this.errors[k] = '');
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
  .form-signin input[type="email"], .form-signin input[type="password"] {
    margin-bottom: -1px;
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
  }
  /* .form-signin input[type="password"] {
    margin-bottom: 10px;
    border-top-left-radius: 0;
    border-top-right-radius: 0;
  } */
  .invert { filter: invert(100%); }
</style>
