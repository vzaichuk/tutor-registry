import {getField, updateField} from 'vuex-map-fields';

export const state = () => ({
  auth: {
    roleChosen: false
  }
});

export const getters = {getField};

export const mutations = {updateField};

export const actions = {
  setRoleChosen({commit}, v) {
    commit('updateField', {path: 'auth.roleChosen', value: v});
  }
};
