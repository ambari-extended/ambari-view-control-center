import DS from 'ember-data';

export default DS.Model.extend({
  version: DS.attr('string'),
  config: DS.attr(),
  deployed: DS.attr('boolean', {default: false}),
  package: DS.belongsTo('package'),

  deploy: function() {
    console.log("Deploying this version.....");
    let adapter = this.get('store').adapterFor(this.constructor.modelName);
    return adapter.deploy(this, true).then(() => {
      this.set('deployed', true);
    });
  }
});
