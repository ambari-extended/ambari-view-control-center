import Ember from 'ember';

export default Ember.Route.extend({
  createSuccess(repo) {
    let repositoryController = this.controllerFor('repositories');
    repositoryController.model.push(repo);
    this.transitionTo('repositories');
  },
  createFailure(reason) {
    console.log("Failed to save the repository. Error:",reason);
    this.transitionTo('repositories');
  },
  actions: {
    close() {
      this.transitionTo('repositories');
    },
    create(data) {
      let repo = this.get('store').createRecord('repository', {name: data.name, scanUrl: data.scanUrl});
      repo.save()
        .then(this.createSuccess.bind(this))
        .catch(this.createFailure.bind(this));
    }
  }
});
