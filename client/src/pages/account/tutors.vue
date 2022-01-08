<template>
  <main role="main">
    <div class="jumbotron py-4">
      <div class="col-sm-12 mx-auto mt-1">
        <h1>My Tutors</h1>
      </div>
    </div>

    <div class="jumbotron py-4">
      <div class="col-sm-12 mx-auto mt-1">
        <h1>All Tutors</h1>
      </div>
    </div>
  </main>
</template>

<script>
export default {
  name: 'page-tutors',

  layout(ctx) {
    return 'account';
  }
};
</script>

