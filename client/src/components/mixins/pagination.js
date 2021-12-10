export default {
  data: function() {
    return {
      pageNumber: 0,
      pageSize: 10,
      paginationHandler: null
    };
  },

  computed: {
    page: {
      get: function() {
        return this.pagination.page + 1;
      },
      set: function(next, prev) {
        this.pagination = {...this.pagination, page: next - 1};
        if (this.paginationHandler) {
          let handler = (typeof this.paginationHandler == 'string')
              ? this[this.paginationHandler]
              : this.paginationHandler;
          handler(this.pagination);
        }
      }
    },

    pagination: {
      get: function() { return {page: this.pageNumber, size: this.pageSize}; },
      set: function(next, prev) {
        this.pageNumber = next.page;
        this.pageSize = next.size;
      }
    }
  }
};
