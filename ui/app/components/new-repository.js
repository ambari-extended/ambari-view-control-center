import Ember from 'ember';

export default Ember.Component.extend({
  repoName: '',
  repoScanUrl: '',
  actions: {
    close() {
      this.sendAction('closeAction');
    },
    save() {
      this.sendAction('createAction', {name: this.get('repoName'), scanUrl: this.get('repoScanUrl')});
    }
  }
});
