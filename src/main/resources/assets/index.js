'use strict'
var Vue = require('vue');
var VueResource = require('vue-resource');

var Card = require('./js/components/card.vue');
var Deck = require('./js/components/deck.vue');
var Player = require('./js/components/player.vue');

Vue.use(VueResource);

Vue.component('card', Card);
Vue.component('deck', Deck);
Vue.component('player', Player);

Vue.transition('cardAnimation', {
  enterClass: 'slideInRight',
  leaveClass: 'fadeOut',
  stagger: function (index) {
    return Math.min(100, index * 5)
  }
})


new Vue ({
    el: "#app",

    ready () {
    },

    events: {
      'card.deal' : function (card) {
        gameState(pokerTable, { type: 'DEAL', card: card }, this)
      },
      'hand.analyze': function () {
        this.$http.post('http://localhost:8080/api/analyze', pokerTable).then(
          (response) => {
            console.log(response.data)
            response.data.players.map((p) => {
              this.$refs["player" + p.id].setHand(p.hand.name)
            })
            this.$refs["player" + response.data.players[0].id].setWinner()
          }
        )
      },
      'restart': function () {
        gameState(pokerTable, { type: 'RESTART' }, this)
      }
    }
});

var pokerTable = {
  cards: [],
  players: [
    { id: 1, cards:[] },
    { id: 2, cards:[] },
    { id: 3, cards:[] },
    { id: 4, cards:[] },
    { id: 5, cards:[] }
  ]
}

function gameState (state, action, instance) {
  console.log(action);
  switch (action.type) {
    case 'DEAL':
      if(state.cards.length < 5) {
        state.cards.push(action.card)
        //send card to community
        instance.$refs.dealer.getCard(action.card)
      }
      else {
        for(var i = 0; i < state.players.length; i++) {
          if(state.players[i].cards.length < 2) {
            state.players[i].cards.push(action.card)
            instance.$refs["player" + (i+1)].getCard(action.card)
            if(i == state.players.length - 1 && state.players[i].cards.length == 2) {
              instance.$broadcast('deck.hide')
            }
            break;
          }
        }
      }
      break;
    case 'RESTART':
      pokerTable.cards = []
      pokerTable.players.map((p) => {
        p.cards = []
      })
      instance.$broadcast('restart')
  }
}
