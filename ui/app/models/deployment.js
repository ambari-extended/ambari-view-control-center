import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  version: DS.belongsTo('package-version')
});
