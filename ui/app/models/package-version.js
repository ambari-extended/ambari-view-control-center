import DS from 'ember-data';

export default DS.Model.extend({
  version: DS.attr('string'),
  config: DS.attr(),
  package: DS.belongsTo('package')
});
