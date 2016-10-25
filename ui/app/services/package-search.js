import Ember from 'ember';

export default Ember.Service.extend({
  store: Ember.inject.service(),

  find(like) {
    return this.get('store').query('package', {like: like});
  }
});
