<template>
  <div class="content">
    <section id="hero">
      <div class="hero-container">
        <a class="hero-logo" data-aos="zoom-in"><img src="/img/hero-logo.png" alt=""></a>
        <h1 data-aos="zoom-in">Welcome To Guitar Tutor Registry</h1>
        <h2 data-aos="fade-up">It is a place where people learn how to play guitar</h2>
        <NuxtLink to="/account" class="btn-get-started scrollto">Get In</NuxtLink>
      </div>
    </section>
  </div>
</template>

<script>
export default {
  name: 'page-index',

  auth: false
}
</script>
