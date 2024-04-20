import '@vaadin/tooltip/theme/lumo/vaadin-tooltip.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/icon/theme/lumo/vaadin-icon.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/login/theme/lumo/vaadin-login-overlay.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '49a3661c3d80271c8b79c869d0142f6996fb5ce7d7008214646cf421d2424535') {
    pending.push(import('./chunks/chunk-6d2c206ef2d4d28f90b87e5e92b44fe12a97e63e4e5a3989bdb2948a16c5ed07.js'));
  }
  if (key === '6704f9cd5315e6100c783d31524e13f0be888026efd047c14a2997f58df4d89e') {
    pending.push(import('./chunks/chunk-5c88e5c982eda2ff470efbd4bc42389788617a266400905d5464bd59945fefdd.js'));
  }
  if (key === '6b32d7f907df3db9743316523622b243a3a18803f7d3b48f368ef5244ce0fea8') {
    pending.push(import('./chunks/chunk-4a0ba27b6211aa5993dc2c0ae45230d12a9212362d03de59963cf5e489c482b0.js'));
  }
  if (key === 'd9eb6402dcbafe24766b0d5fab55715aef3f41939d38b291cb5bdaa632053f1c') {
    pending.push(import('./chunks/chunk-12dfc31e6aee380afe6448ef3cb0a216b03bdf69064e55c1be8c9b312237eeb5.js'));
  }
  if (key === '1cef46c31333b8418ae5d8d9961c4d169930c7785cb6ab8ce5a6a88eed1e2e5c') {
    pending.push(import('./chunks/chunk-eef51dcdb5313b265ec6c21e9c43ba9b2787e0f388ab5bf10e92acab5a61e3e0.js'));
  }
  if (key === 'e3c002316e8e354dd60effb32070c2c6aef8c768e54f069046bb36c56ba058c9') {
    pending.push(import('./chunks/chunk-c3b4d686d3fc97a922dbb9dc3c101ce9767c5375bdae266d12a33f84a0a8fbba.js'));
  }
  if (key === 'a75b51b5c944683a1c3d37c94f02bfca5a096aef90b197a73869de93ebe236f0') {
    pending.push(import('./chunks/chunk-0440bd9a24c0aeb5306bfa334a56471396b611653dda438c69b43d351cfa69d8.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;
window.Vaadin.Flow.resetFocus = () => {
 let ae=document.activeElement;
 while(ae&&ae.shadowRoot) ae = ae.shadowRoot.activeElement;
 return !ae || ae.blur() || ae.focus() || true;
}