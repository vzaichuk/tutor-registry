export default {
  methods: {
    notifySuccess: function(message, title) { compose(this, 'success', message, title || 'Success'); },
    notifyWarning: function(message, title) { compose(this, 'warning', message, title || 'Warning'); },
    notifyError: function(message, title) { compose(this, 'danger', message, title || 'Error'); }
  }
};

function compose(vm, variant, message, title) {
  vm.$bvToast.toast(message, {title, variant, solid: true});
}
