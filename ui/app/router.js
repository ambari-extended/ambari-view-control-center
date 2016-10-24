import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('workspace');
  this.route('deployments', function() {
    this.route('add');
  });
  this.route('clusters');
  this.route('instances');
  this.route('repositories', function() {
    this.route('new');
  });
  this.route('hosts', function() {
    this.route('new');
  });
  this.route('about');
  this.route('settings');


});

export default Router;
