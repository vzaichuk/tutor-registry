<template>
  <div class="wrapper text-center">
    <form class="form-signin">
      <img class="mb-4 invert" src="/img/hero-logo.png" alt="" width="72" height="72" />

      <p>Just a sec...</p>
      
      <p class="mt-5 mb-3 text-muted">© 2021-{{ year }}</p>
    </form>
  </div>
</template>

<script>
import {mapActions} from 'vuex';
import errorHanlderMixin from '~/components/mixins/error-handler';

export default {
  name: 'page-auth-login',

  layout: 'auth',

  auth: 'guest',

  mixins: [errorHanlderMixin],

  fetch() {
    this.authenticate();
  },

  data: function() {
    return {
    };
  },

  computed: {
    year() {
      return (new Date()).getFullYear();
    }
  },

  methods: {
    ...mapActions(['setRoleChosen']),

    authenticate() {
      this.$repository.authentication
          .authenticateOauth2(this.$route.params.id, this.$route.query.code)
          .then(response => {
            console.log(response);
            this.$auth.setUserToken(response.access_token, response.refresh_token);
            this.$router.push({path: '/account'});
          })
          .catch(error => this.$errorHandler.handle(this, error));
    }
  },

  created() {
    this.setRoleChosen(false);
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
