<template>
    <div>
        <img class="card-face" :src="cardFace"/>
    </div>
</template>

<script>
    export default {
        props: ['value'],
        computed: {
            cardFace: function () {
                return `/img/deck/${this.value}.png`
            }
        }
    } 
</script>
<style>
    .card-face {
        height: 110px;
        box-shadow: 2px 2px 2px #2D5572;
    }
</style>
