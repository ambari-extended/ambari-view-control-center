import Ember from 'ember';

export default Ember.Route.extend({
  model: function() {
    return this.get('store').findAll('repository');
  },

  deleteSuccessful() {
    console.log("Delete successful");
  },

  deleteFailed(error) {
    console.log("Delete failed", error);
  },

  startSuccessful() {
    console.log("Start successful");
  },

  startFailed(error) {
    console.log("Start failed", error);
  },

  stopSuccessful() {
    console.log("Stop successful");
  },

  stopFailed(error) {
    console.log("Stop failed", error);
  },
  actions: {
    start(repo) {
      repo.start()
        .then(this.startSuccessful.bind(this))
        .catch(this.startFailed.bind(this));
    },

    delete(repo) {
      repo.destroyRecord()
        .then(this.deleteSuccessful.bind(this))
        .catch(this.deleteFailed.bind(this));
    },

    stop(repo) {
      repo.stop()
        .then(this.stopSuccessful.bind(this))
        .catch(this.stopFailed.bind(this));
    }


  }
});
