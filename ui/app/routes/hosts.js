import Ember from 'ember';

export default Ember.Route.extend({
  model: function() {
    return this.store.findAll('host');
  },

  actions: {
    delete(model) {
      console.log("Deleteing model");
      model.destroyRecord();
    }
  }
});
