import ApplicationSerializer from './application';

export default ApplicationSerializer.extend({
  serialize() {
    let json = this._super(...arguments);
    return {name: json.name, scanUrl: json.scanUrl};
  }
});
