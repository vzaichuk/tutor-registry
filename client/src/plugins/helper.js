export default async (ctx, inject) => {
  inject('helper', {
    getUrlPrefix: function() { return '/client'; }
  });

  inject('errorHandler', {
    handle: function(vm, error, errorsCallback) {
      if (error.response) {
        if (error.response.status == 403) {
          window.location.href = ctx.app.$helper.getUrlPrefix() + '/auth/login';
        } else if (error.response.data.errors) {
          ctx.app.$notification.notifyError(vm, error.response.data.message
              || (!errorsCallback && Object.values(error.response.data.errors)[0])
              || 'Знайдено помилки');
          errorsCallback && errorsCallback(error.response.data.errors);
        } else {
          ctx.app.$notification.notifyError(vm, error.response.data.message);
        }
      } else {
        ctx.app.$notification.notifyError(vm, 'Сталась невідома помилка.');
      }
    }
  });
};
