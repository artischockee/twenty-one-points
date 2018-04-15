# Twenty-One Points
Game "Twenty-One Points" (russian version) with Graphical User Interface

## Description
From [Wikipedia](https://en.wikipedia.org/wiki/Blackjack):
> Blackjack, also known as twenty-one, is a comparing card game between usually several players and a dealer, where each player in turn competes against the dealer, but players do not play against each other. It is played with one or more decks of 52 cards, and is the most widely played casino banking game in the world.

This program, however, represents the russian version of the game (you can read about it on [Wikipedia (russian, polish and czech languages only)](https://ru.wikipedia.org/wiki/%D0%9E%D1%87%D0%BA%D0%BE_(%D0%B8%D0%B3%D1%80%D0%B0)))

The main difference between original blackjack and "twenty-one points" (i.e. russian blackjack) is in the deck size. Russian blackjack has 36 cards in a deck (so-called "standard russian deck"), since there are no cards from "2" to "5" in there.

Because of that, the cards like Jack (J), Queen (Q) and King (K) have their weight not 10, but 2, 3 and 4 accordingly. The Ace (A) has his total weight of 11. Another cards have their weight by the numbering.

## Goals
- [x] Write basic Model's logic
- [x] Make an interaction for players's table
- [x] Get rid of setting font for View's labels
- [ ] Unite the Model and the View using Controller
- [ ] Remove unnecessary 'TurnStatement' enum and make the code easier
- [ ] Add the i18n to the application

## Disclaimer
The author **does not** appreciate gambling, so the development of this program wasn't in his plans. It was a customer's desire. But anyway, this development gave the author the new experience in programming. The author was honestly trying (and he still tries) to make a good, handy design of the program, therefore please, give your feedback. Thank you!

## License
This work is licensed under [the MIT License](https://opensource.org/licenses/MIT)
