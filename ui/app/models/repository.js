import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  scanUrl: DS.attr('string'),
  scanStatus: DS.attr('string'),
  running: DS.attr('boolean'),
  lastScannedAt: DS.attr('date'),

  start() {
    let adapter = this.get('store').adapterFor(this.constructor.modelName);
    return adapter.performAction(this, {action: 'START'}).then(() => {
      this.set('running', true);
      this.set('scanStatus', 'RUNNING');
    });
  },

stop() {
  let adapter = this.get('store').adapterFor(this.constructor.modelName);
  return adapter.performAction(this, {action: 'STOP'}).then(() => {
      this.set('running', false);
      this.set('scanStatus', 'STOPPED');
    });
}
});
