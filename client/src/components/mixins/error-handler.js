import toasterMixin from '~/components/mixins/toaster';

const errorHandlers = [
  {
    on: e => e.response && e.response.data && e.response.data.error_description,
    process: (e, vm) => vm.notifyError(e.response.data.error_description)
  }, {
    on: e => true,
    process: (e, vm) => vm.notifyError('Error occured. Handler is not defined.')
  }
];

export default {
  mixins: [toasterMixin],

  methods: {
    handleError(error) {
      for (let handler of errorHandlers) {
        if (handler.on(error)) {
          handler.process(error, this);
        }
      }
    }
  }
};
