import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  scanUrl: DS.attr('string'),
  scanStatus: DS.attr('string'),
  isRunning: DS.attr('boolean'),
  lastScannedAt: DS.attr('date')
});
