import {getField, updateField} from 'vuex-map-fields';
import {isAdmin} from '~/utils/auth-helper';

export const state = () => ({
  auth: {
    roleChosen: false
  },
  unseenNotifications: 0
});

export const getters = {getField};

export const mutations = {updateField};

export const actions = {
  setRoleChosen({commit}, v) {
    commit('updateField', {path: 'auth.roleChosen', value: v});
  },

  refreshUnseenNotifications(ctx) {
    const handler = this.$auth.loggedIn && isAdmin(this.$auth.user)
        ? () => this.$repository.notification.getAmountByUserId(0)
        : () => this.$repository.notification.getAmount();

    handler().then(r => ctx.commit('updateField', {path: 'unseenNotifications', value: r}));
  }
};
