import Ember from 'ember';

export default Ember.Component.extend({
  hostName: '',
  hostKey: '',
  actions: {
    close() {
      this.sendAction('closeAction');
    },
    save() {
      this.sendAction('createAction', {name: this.get('hostName'), key: this.get('hostKey')});
    }
  }
});
