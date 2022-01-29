<template>
  <div class="root">
    <div class="container">
      <nav class="navbar navbar-expand-lg navbar-light bg-light rounded pr-4 py-3 my-3">
        <NuxtLink to="/" class="navbar-brand">Home</NuxtLink>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample09" aria-controls="navbarsExample09" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div v-if="isAdmin()" class="collapse navbar-collapse" id="navbarsExample09">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <NuxtLink to="/admin/applications" class="nav-link">Applications</NuxtLink>
            </li>
          </ul>
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <NuxtLink to="/notifications" :class="['nav-link', unseenNotifications > 0 ? 'labelable' : '']">Notifications</NuxtLink>
            </li>
            <li class="nav-item">
              <a href="#" @click="logout" class="nav-link">Log out</a>
            </li>
          </ul>
        </div>
        <div v-else class="collapse navbar-collapse" id="navbarsExample09">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <NuxtLink to="/account" class="nav-link">Account</NuxtLink>
            </li>
            <li v-if="isTutor()" class="nav-item">
              <NuxtLink to="/account/students" class="nav-link">Students</NuxtLink>
            </li>
            <li v-if="isStudent()" class="nav-item">
              <NuxtLink to="/account/tutors" class="nav-link">Tutors</NuxtLink>
            </li>
          </ul>
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <NuxtLink to="/notifications" :class="['nav-link', unseenNotifications > 0 ? 'labelable' : '']">Notifications</NuxtLink>
            </li>
            <li class="nav-item">
              <a href="#" @click="logout" class="nav-link">Log out</a>
            </li>
          </ul>
        </div>
      </nav>

      <nuxt v-if="isActive() || isAdmin()" />
      <div v-else>
        <div v-if="isRejected()">
          <h2 class="text-center mt-5">Your profile was rejected</h2>
        </div>
        <div v-else>
          <h2 class="text-center mt-5">Your profile is on review</h2>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {isAdmin, isTutor, isStudent} from '~/utils/auth-helper';
import notifiableMixin from '~/components/mixins/notifiable';

export default {
  name: 'layout-account',

  mixins: [notifiableMixin],

  fetch() {
    this.refreshUnseenNotifications();
  },

  computed: {
    unseenNotifications() {
      return this.$store.getters['getField']('unseenNotifications');
    }
  },

  methods: {
    logout() {
      this.$auth.logout().then(() => this.$router.push('/'));
    },

    isActive() {
      return this.$auth.loggedIn && this.$auth.user.status == 1;
    },

    isRejected() {
      return this.$auth.loggedIn && this.$auth.user.status == 2;
    },

    isAdmin() {
      return this.$auth.loggedIn && isAdmin(this.$auth.user);
    },

    isTutor() {
      return this.$auth.loggedIn && isTutor(this.$auth.user);
    },

    isStudent() {
      return this.$auth.loggedIn && isStudent(this.$auth.user);
    }
  }
}
</script>

<style scoped>
  .labelable::after {
    position: absolute;
    right:1px;
    top:10px;
    content: '';
    background-color:#e62a59;
    border-radius:50%;
    width:10px;
    height:10px;
  }
</style>
