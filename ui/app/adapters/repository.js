import ApplicationAdapter from './application';

export default ApplicationAdapter.extend({
  performAction(model, data) {
    let url = this.urlForFindRecord(model.get("id"), model.constructor.modelName) + "/action";
    let payload = {};
    payload[`${model.constructor.modelName}`] = data;
    return this.ajax(url, "POST", {data: payload});
  }
});
