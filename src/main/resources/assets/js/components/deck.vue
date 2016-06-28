<template>
    <div class="deck" v-show="showDeck">
        <card
          class="card animated"
          v-for="(index, card) in cards"
          :value="card"
          :style="{left: (index * 20) + 'px'}"
          transition="cardAnimation"
          stagger="25"
          @click="dealCard(card)"
          >
        </card>
    </div>
    <div class="buttons" v-show="!showDeck">
      <button @click="analyzeHands">Analyze Hands</button>
      <button @click="restart">Restart</button>
    </div>
</template>
    
<script>
  export default {
    data () {
      return {
        cards: [],
        showDeck: true
      }
    },
    ready () {
      this.getDeck()
    },
    methods: {
      getDeck () {
        this.$http.get('/api/deck').then(
          (response) => {
            response.data.map((card) => { this.cards.push(card) })
        });
      },
      dealCard (card) {
        this.cards.$remove(card)
        this.$dispatch('card.deal', card)
      },
      analyzeHands () {
        this.$dispatch('hand.analyze')
      },
      restart () {
        this.showDeck = true;
        while(this.cards.length > 0)
          this.cards.pop()
        this.getDeck()
        this.$dispatch('restart')
      }
    },
    events: {
      'deck.hide': function () {
        this.showDeck = false;
      }
    }
  }
</script>

<style>
  .deck {
    position: relative;
    height: 140px;
    margin-top: 25px;
  }
  .deck .card {
      position: absolute;
  }
  .deck .card:hover {
    top: -10px;
  }
  .buttons {
    display: flex;
    height: 140px;
    justify-content: center;
  }
  .buttons button {
    text-align: center;
    display: flex;
    align-items: center;
    height: 30px;
    width: 30%;
  }
</style>
