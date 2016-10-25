import ApplicationAdapter from './application';

export default ApplicationAdapter.extend({

  deploy(model, publish = true) {
    let url = this.urlForUpdateRecord(model.get('id'), 'versions') + "/deploy";
    return this.ajax(url, 'POST');
  }
});
