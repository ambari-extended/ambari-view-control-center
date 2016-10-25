import Ember from 'ember';

export default Ember.Component.extend({
  classNames: ['col-md-12', 'item'],
  versionList: Ember.computed('item', function() {
    return this.get('item.versions').map(x => {
      return x.get('version');
    });
  }),
  versionMap: Ember.computed('item', function() {
    return this.get('item.versions').reduce(function(previousValue, item) {
      previousValue[item.get('version')] = item;
      return previousValue;
    }, {});
  }),
  selectedVersion: Ember.computed('versionList', function() {
    return this.get('versionList.firstObject');
  }),
  selectedVersionEntry: Ember.computed('selectedVersion', function() {
    return this.get('versionMap')[this.get('selectedVersion')];
  }),

  actions: {
    deploy() {
      this.sendAction('deployAction', this.get('item'), this.get('selectedVersionEntry'));
    }
  }
});
