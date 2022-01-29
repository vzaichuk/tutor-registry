<template>
  <main role="main" class="text-center" style="height:100vh;">
    <div style="margin-top:calc(50vh - 200px);">
      <img class="mb-4" src="/img/matrix.jpg" style="max-width:400px;" />
      <div>
        <h4 class="mb-4">Now, choose your side wisely</h4>
        <button class="btn btn-danger px-5 mr-5" @click="chooseRole('ROLE_STUDENT')">Student</button>
        <button class="btn btn-primary px-5" @click="chooseRole('ROLE_TUTOR')">Tutor</button>
      </div>
    </div>
  </main>
</template>

<script>
import {mapActions} from 'vuex';

export default {
  name: 'page-account-role',

  layout: 'auth',

  methods: {
    ...mapActions(['setRoleChosen']),

    chooseRole(role) {
      this.$repository.authentication.setRole(role)
          .then(response => this.$repository.authentication.refreshToken(this.$auth.strategy.refreshToken.get()))
          .then(response => {
            this.setRoleChosen(true);
            this.$auth.setUserToken(response.access_token, response.refresh_token);
            this.$router.push({path: '/account'});
          })
          .catch(error => this.$errorHandler.handle(this, error));
    }
  }
};
</script>
