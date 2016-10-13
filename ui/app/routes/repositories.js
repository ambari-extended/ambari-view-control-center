import Ember from 'ember';

export default Ember.Route.extend({
  model: function() {
    return this.get('store').findAll('repository');
  },
  actions: {
    start(repo) {
      console.log("Starting the repo", repo);
    },

    delete(repo) {
      console.log("deleting the repo", repo);
    },

    stop(repo) {
      console.log("Stoping the repo", repo);
    }


  }
});
