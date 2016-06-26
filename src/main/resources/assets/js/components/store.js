var Vue = require('vue');
var createStore = require('redux').createStore;

function gameState(state = 0, action) {
  switch(action.type) {
    case 'CARD':
      console.log(action);
      return state + 1;
  }
}

var store = createStore(gameState);
store.subscribe(() => { console.log(store.getState()) })

module.exports = store;
