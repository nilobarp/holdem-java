<template>
  <div class="player">
    <div class="avatar">
      <img :src="avatarImg"/>
      <div>{{ playerName }}</div>
    </div>
    <card
      class="card animated"
      v-for="card in cards"
      track-by="$index"
      :value="card"
      transition="cardAnimation"
      stagger="50"
    >
    </card>
    <div>{{ rank }}</div>
    <div v-show="winner">Winner</div>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        cards: [],
        playerName: '',
        avatarImg: '',
        rank: '',
        winner: false
      }
    },
    ready () {
      this.$http.get('http://localhost:8080/api/profile').then(
        (response) => {
          this.playerName = response.data.name;
          this.avatarImg = response.data.avatar;
        }
      );
    },
    methods: {
      getCard(card) {
        this.cards.push(card)
      },
      setHand(rank) {
        this.rank = rank
      },
      setWinner() {
        this.winner = true
      }
    },
    events: {
      'restart': function () {
        console.log('Restart')
        this.cards = []
        this.rank = ''
        this.winner = false
      }
    }
  }
</script>

<style>
  .player {
    margin: 5px;
    min-width: 205px;
    min-height: 180px;
    diaplay: inline-block;
    text-align: center;
    background: #E2E2DF;
    box-shadow: 2px 2px 2px #777;
  }
  .player .card {
    display: inline-block;
    height: 110px;
    box-shadow: 2px 2px 2px #eee;
    margin-right: 10px;
  }
  .avatar {
    text-align: left;
    background: #A0ADAC;
    display: flex;
  }
  .avatar img {
    display: flex;
    height: 40px;
    border-radius: 20px;
    margin: 5px;
  }
  .avatar div {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 60px;
    font-size: 1em;
  }
</style>
