import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  repositoryId: DS.attr('number'),
  versions: DS.hasMany('packageVersion')
});
