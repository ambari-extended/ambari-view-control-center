import Ember from 'ember';

export default Ember.Route.extend({
  deploySuccess(data) {
    this.get('store').pushPayload(data);
    this.transitionTo('deployments');
  },
  deployFailure(reason) {

    console.log("Failed to deploy. Error:",reason);
    this.transitionTo('deployments');
  },
  actions: {
    close() {
      this.transitionTo('deployments');
    },
    deploy(pack, version) {
      version.deploy()
        .then(this.deploySuccess.bind(this))
        .catch(this.deployFailure.bind(this));
    }
  }
});
