import Ember from 'ember';

export default Ember.Route.extend({
  model: function() {
    return [
      {
        name: "Hortonworks",
        scanUrl: "http://registry.ambari.hortonworks.com",
        lastScannedAt: new Date(),
        scanStatus: "NOT STARTED",
        isRunning: false
      },
      {
        name: "Cloudera",
        scanUrl: "http://registry.ambari.cloudera.com",
        lastScannedAt: new Date(),
        scanStatus: "STARTED",
        isRunning: true
      }
    ];
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
