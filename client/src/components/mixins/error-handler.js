import toasterMixin from '~/components/mixins/toaster';

export default {
  mixins: [toasterMixin],

  methods: {
    handleError(error) {
      this.notifyError('Error happened');
      console.log(Object.entries(error));
    }
  }
};
