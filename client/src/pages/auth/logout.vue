<template>
</template>

<script>
export default {
  created() {
    this.$auth.logout().then(this.$router.push({path: '/'}));
  }
}
</script>