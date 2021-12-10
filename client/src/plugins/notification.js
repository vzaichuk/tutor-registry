export default async (ctx, inject) => {
  inject('notification', {
    notifySuccess: function(vm, message, title) { compose(vm, 'success', message, title || 'Success'); },
    notifyWarning: function(vm, message, title) { compose(vm, 'warning', message, title || 'Warning'); },
    notifyError: function(vm, message, title) { compose(vm, 'danger', message, title || 'Error'); }
  });
};

function compose(vm, variant, message, title) {
  vm.$bvToast.toast(message, {title, variant, solid: true});
}
