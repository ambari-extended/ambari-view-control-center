import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('dashboard');
  this.route('clusters');
  this.route('views');
  this.route('repositories', function() {
    this.route('new');
  });
  this.route('hosts');
  this.route('about');
  this.route('settings');
});

export default Router;
