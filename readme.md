#Poker Hand Analyzer

[![Build Status](https://travis-ci.org/nilobarp/holdem.svg?branch=master)](https://travis-ci.org/nilobarp/holdem)

##Background
Each hand is passed through a reducer method that determines hand rank and also calculates relative score based on card relevance of hand.

Hands are first sorted by rank, if two ranks are same a tie occurs.
In case of tie, we calculate poker score of the hands and use this
score to find the winner.

Cards are stored in an int array along with their suit information.
Each suit is weighed by a numeric value as:

| Suit              | Weight  |
| ---------------| ---------:|
| Club             | 100       |
| Diamond      | 200       |
| Hearts          | 300       |
| Spade          | 400       |

The value of each suit is added to the card's face value and stored in the Integer array. This distinguishes each card in the deck and can be easily analyzed for hand type and score calculations. So the hand "♥3 ♥5 ♣3 ♠5 ♥6 ♥T ♠3" will be stored as `Interger[]{303, 305, 103, 305, 306, 310, 403}` and on analysis this will evalute to Full House.

##Analyzer class
At the core of it, analyzer takes a `table` object which is a simple container class for the community cards and players. Each `Player` wraps a `Hand` object having `name`, `rank` & `score` properties.

`run` method of `Analyzer` class churns through each player and passes the community cards and player's hold cards to a `reduce`r method.

###Reducer method
- We start by combining the commuity and hold cards 

|community|hold|hand|
|---------|----|----|
|♥3 ♥5 ♣3 ♠5 ♥6|♥T ♠3|♥3 ♥5 ♣3 ♠5 ♥6 ♥T ♠3

- Then represent hand numerically `Interger[]{303, 305, 103, 305, 306, 310, 403}`
- Next a hash is created from the card values. This hash represents card frequency. i.e. if 5 appears 3 times then the hash will be `{5: 3}`. Applying this to the hand we obtain `{3:3, 5:2, 6: 1, 10: 1}`
- Up next, card array is sorted using the hash for relevance. We use number of appearance of cards for relevance factor. In this example 3's will bubble up to top followed by 5's `Interger[]{303, 103, 403, 305, 405, 310, 306}` (value of suit doesn't matter for the ordering)

###Score calculation
Once we have the card's array ordered by relevance, a score can be easily calculated by bit shifting the card vaues by 4 places. With the card array, last element of the array is used as is, second last gets shifted by 4 places, third last by 8 places and so on. `3<<24|3<<20|3<<16|5<<12|5<<8|10<<4|6 = 53695910`. This score is then used to find winning hands in addition to ranks.

###Build steps
####Frontend
To build the JS portion:
- Change to `holdem/src/main/resources/assets`
- Install dependencies `npm install`
- Build Vue components `npm run gulp` (see `package.json` for otehr available commands)

####Backend
Build with maven
- From project root, run: `mvn install`

###Test
- From project root, run: `mvn test`

###Run
- Start the server using `config.yml` from project root.
`java -jar target/holdem-1.0-SNAPSHOT.jar server config.yml`
- Open browser to `http://localhost:9000`

###REST End Points

| Verb | End Point | Purpose |
|-------:|:-------------|:-----------|
| POST | /api/analyze (com.nilobarp.holdem.Game) | Runs analysis on `Table` object and returns hand ranks|
| GET   |  /api/deck (com.nilobarp.holdem.Deck) | Returns a shuffled deck instance |
|  GET  |   /api/profile (com.nilobarp.holdem.Profile) | Returns a random player profile |
