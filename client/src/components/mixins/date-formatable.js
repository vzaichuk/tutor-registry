export default {
  methods: {
    formatDateFromTimestamp: function(timestamp) {
      const dt = new Date(timestamp * 1000);
      return dt.toLocaleString('ua-UK');
    },

    formatDate: function(timestamp) {
      return this.formatDateFromTimestamp(timestamp);
    }
  }
};
