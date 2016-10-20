import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  keyfileContent: DS.attr('string'),
  keyfileLocation: DS.attr('string')
});
