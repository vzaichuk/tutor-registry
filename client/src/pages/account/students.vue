<template>
  <main role="main">
    <div class="mb-5">
      <div class="jumbotron py-4">
        <div class="col-sm-12 mx-auto mt-1">
          <h1>My students</h1>
        </div>
      </div>

      <div v-if="students && students.length > 0" class="row">
        <div v-for="student in students" :key="student.id" class="col-md-3 mb-3">
          <div class="card">
            <img class="card-img-top" src="data:image/svg+xml;charset=UTF-8,%3Csvg%20width%3D%22286%22%20height%3D%22180%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20viewBox%3D%220%200%20286%20180%22%20preserveAspectRatio%3D%22none%22%3E%3Cdefs%3E%3Cstyle%20type%3D%22text%2Fcss%22%3E%23holder_17e72f9ab78%20text%20%7B%20fill%3Argba(255%2C255%2C255%2C.75)%3Bfont-weight%3Anormal%3Bfont-family%3AHelvetica%2C%20monospace%3Bfont-size%3A14pt%20%7D%20%3C%2Fstyle%3E%3C%2Fdefs%3E%3Cg%20id%3D%22holder_17e72f9ab78%22%3E%3Crect%20width%3D%22286%22%20height%3D%22180%22%20fill%3D%22%23777%22%3E%3C%2Frect%3E%3Cg%3E%3Ctext%20x%3D%2299.421875%22%20y%3D%2296.6%22%3EImage%20cap%3C%2Ftext%3E%3C%2Fg%3E%3C%2Fg%3E%3C%2Fsvg%3E" alt="Card image cap">
            <div class="card-body">
              <h5 class="card-title">{{ student.username }}</h5>
              <p v-if="student.bio" class="card-text">{{ student.bio }}</p>
              <p v-else class="card-text" style="opacity:0.4">Bio is empty</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import errorHandlerMixin from '~/components/mixins/error-handler';
import notifiableMixin from '~/components/mixins/notifiable';

export default {
  name: 'page-students',

  layout: 'account',

  mixins: [errorHandlerMixin, notifiableMixin],

  fetch() {
    this.getMyStudents();
  },

  data() {
    return {
      students: []
    };
  },

  methods: {
    getMyStudents() {
      this.$repository.account.getMyStudents()
          .then(response => this.students = response)
          .catch(this.handleError);
    }
  }
};
</script>

