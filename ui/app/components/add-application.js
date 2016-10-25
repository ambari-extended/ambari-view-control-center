import Ember from 'ember';

export default Ember.Component.extend({
  packageSearch: Ember.inject.service(),
  packages: [],
  searchField: '',
  actions: {
    search() {
      this.get('packageSearch').find(this.get('searchField')).then((data) => {
        this.set('packages', data);
      });
    },

    deployVersion(pack, version) {
      this.sendAction('deployAction', pack, version);
    },

    close() {
      this.sendAction('closeAction');
    }
  }
});
