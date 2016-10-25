import DS from 'ember-data';

export default DS.Model.extend({
  version: DS.attr('string'),
  config: DS.attr(),
  deployed: DS.attr('boolean', {default: false}),
  package: DS.belongsTo('package'),

  deploy: function() {
    let adapter = this.get('store').adapterFor(this.constructor.modelName);
    return adapter.deploy(this, true).then((data) => {
      this.set('deployed', true);
      return data;
    });
  }
});
