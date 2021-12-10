<template>
  <div class="root">
    <div class="container">
      <nav class="navbar navbar-expand-lg navbar-light bg-light rounded pr-4 py-3 my-3">
        <NuxtLink to="/" class="navbar-brand">Home</NuxtLink>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExample09">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <NuxtLink to="/account" class="nav-link">Account</NuxtLink>
            </li>
            <li class="nav-item">
              <NuxtLink to="/tutors" class="nav-link">Tutors</NuxtLink>
            </li>
          </ul>
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <NuxtLink to="/settings" class="nav-link">Settings</NuxtLink>
            </li>
            <li class="nav-item">
              <a href="#" @click="logout" class="nav-link">Log out</a>
            </li>
          </ul>
        </div>
      </nav>
      
      <nuxt />
    </div>
  </div>
</template>

<script>
export default {
  name: 'layout-account',

  methods: {
    logout() {
      this.$auth.logout().then(() => this.$router.push('/'));
    }
  }
}
</script>
