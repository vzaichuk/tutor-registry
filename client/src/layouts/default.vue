<template>
  <nuxt />
</template>

<script>
export default {
  name: 'default',

  data: function() {
    return {
      menuItems: {
        '/': {title: 'Main', icon: 'bank', path: '/'},
        '/auth/logout': {title: 'Log out', icon: 'button-power', path: '/auth/logout'}
      }
    };
  },

  computed: {
    title: function() {
      return (this.menuItems[this.$route.path] && this.menuItems[this.$route.path].title)
          || 'Dashboard';
    }
  }
};
</script>
