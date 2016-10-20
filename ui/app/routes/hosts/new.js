import Ember from 'ember';

export default Ember.Route.extend({
  createSuccess(repo) {
    this.transitionTo('hosts');
  },
  createFailure(reason) {

    console.log("Failed to save the Host. Error:",reason);
    this.transitionTo('hosts');
  },
  actions: {
    close() {
      this.transitionTo('hosts');
    },
    create(data) {
      let repo = this.get('store').createRecord('host', {name: data.name, keyfileContent: data.key});
      repo.save()
        .then(this.createSuccess.bind(this))
        .catch(this.createFailure.bind(this));
    }
  }
});
