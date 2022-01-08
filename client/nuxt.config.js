module.exports = {
  dev: process.env.NODE_ENV !== 'production',

  server: {
    host: '0' // default: localhost
  },

  target: 'static',
  ssr: false, // Disable Server Side rendering

  css: [
    '~/static/css/style.css'
  ],

  srcDir: 'src/',

  head: {
    title: 'Admin',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1, shrink-to-fit=no' }
    ]
  },

  modules: [
    '@nuxtjs/axios',
    '@nuxtjs/auth-next',
    'bootstrap-vue/nuxt'
  ],

  plugins: [
    '~/plugins/repository',
    '~/plugins/helper',
    '~/plugins/notification',
    '~/plugins/axios'
  ],

  axios: {
    proxy: (process.env.NODE_ENV !== 'production'),
    baseUrl: '/'
  },

  auth: {
    redirect: { login: '/auth/login' },
    strategies: {
      local: {
        scheme: 'refresh',
        token: {
          property: 'access_token',
          maxAge: 60 * 15
        },
        refreshToken: {
          property: 'refresh_token',
          data: 'refresh_token',
          maxAge: 60 * 60 * 24 * 30
        },
        user: {
          property: 'user'
        },
        endpoints: {
          login: { url: '/authentication/login/credentials', method: 'post' },
          refresh: { url: '/authentication/login/refresh', method: 'post' },
          user: { url: '/account/me', method: 'get' },
          logout: false
        }
      }
    }
  },

  router: {
    base: (process.env.PATH_PREFIX || '/client'),
    middleware: [
      'auth', 'router'
    ]
  },

  proxy: {
    '/authentication': {target: 'http://localhost:8080', secure: false},
    '/account': {target: 'http://localhost:8080', secure: false},
    '/notification': {target: 'http://localhost:8080', secure: false}
  },

  generate: {
    dir: process.env.DIST_DIR || 'dist',
    devtools: process.env.NODE_ENV !== 'production'
  }
}
