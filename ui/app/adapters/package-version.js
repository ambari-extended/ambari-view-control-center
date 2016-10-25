import ApplicationAdapter from './application';

export default ApplicationAdapter.extend({

  deploy(model) {
    let url = this.urlForUpdateRecord(model.get('id'), 'versions') + "/deploy";
    return this.ajax(url, 'POST');
  }
});
