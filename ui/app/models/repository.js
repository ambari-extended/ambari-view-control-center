import DS from 'ember-data';

export default DS.Model.extend({
  name: DS.attr('string'),
  scanUrl: DS.attr('string'),
  scanStatus: DS.attr('string'),
  isRunning: DS.attr('boolean'),
  lastScannedAt: DS.attr('date'),

  start() {
    let adapter = this.get('store').adapterFor(this.constructor.modelName);
    return adapter.performAction(this, {action: 'START'}).then(() => {
      this.set('isRunning', true);
    });
  },

  stop() {
    let adapter = this.get('store').adapterFor(this.constructor.modelName);
    return adapter.performAction(this, {action: 'STOP'}).then(() => {
      this.set('isRunning', false);
    });
  }
});
