import Ember from 'ember';

export default Ember.Component.extend({
  menuConfig: [
    {
      name: 'Dashboard',
      link: 'dashboard',
      icon: 'empire'
    },
    {
      name: 'Clusters',
      link: 'clusters',
      icon: 'list-alt'
    },
    {
      name: 'Views',
      link: 'views',
      icon: 'list'
    }
  ]
});
