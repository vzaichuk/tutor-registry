<template>
  <div class="main">
    <div class="jumbotron">
      <div class="col-sm-10 mx-auto">
        <h1>Notifications</h1>
      </div>
    </div>

    <div v-if="notifications.length" class="mb-5">
      <div class="mb-2">
        <a class="pointable" @click="remove(null)">Remove all seen</a>
      </div>

      <div v-for="message in notifications" :key="message.id" class="p-3 mb-2" :style="{'background-color': (message.seen ? '#fff' : '#eceff3'), 'border-radius':'6px'}">
        <div class="row">
          <div class="col-md-2"><small><i>{{ formatDate(message.created) }}</i></small></div>
          <div class="col-md-8">{{ message.content }}</div>
          <div class="col-md-2 text-right">
            <a v-if="!message.seen" class="pointable mr-2" @click="seen(message.id)">Seen</a>
            <a class="pointable" @click="remove(message.id)">Remove</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {isAdmin} from '~/utils/auth-helper';
import erroHandlerMixin from '~/components/mixins/error-handler';
import notifiableMixin from '~/components/mixins/notifiable';

export default {
  name: 'page-notifications',

  layout: 'account',

  mixins: [erroHandlerMixin, notifiableMixin],

  fetch() {
    this.getNotifications();
  },

  data() {
    return {
      notifications: []
    };
  },

  methods: {
    getNotifications() {
      const getter = this.$auth.loggedIn && isAdmin(this.$auth.user)
          ? () => this.$repository.notification.getAllByUserId(0)
          : () => this.$repository.notification.getAll();

      getter()
          .then(response => this.notifications = response.reverse())
          .then(this.refreshUnseenNotifications)
          .catch(this.handleError);
    },

    formatDate(strDate) {
      const date = new Date(strDate);
      return date.toLocaleString();
    },

    seen(id) {
      const handler = this.$auth.loggedIn && isAdmin(this.$auth.user)
          ? () => this.$repository.notification.seenByUserId(0, id)
          : () => this.$repository.notification.seen(id);
      handler().then(this.getNotifications).catch(this.handleError);
    },

    remove(id) {
      let handler = null;
      if (this.$auth.loggedIn && isAdmin(this.$auth.user)) {
        handler = id
          ? () => this.$repository.notification.removeByUserId(0, id)
          : () => this.$repository.notification.removeAllByUserId(0);
      } else {
        handler = id
          ? () => this.$repository.notification.remove(id)
          : () => this.$repository.notification.removeAll();
      }

      handler().then(this.getNotifications).catch(this.handleError);
    }
  }
}
</script>

<style scoped>
  .pointable:hover { cursor:pointer; text-decoration: underline; }
</style>
