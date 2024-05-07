sources for the prototype bot
Here we will prototype the ideas for our bot before implementing them in the main_bot

to run the main bot (from the repo root folder) for 'Etapa 1':
	./game_binaries/halite -d "W H" -n 1 -s 42 "python3 main_bot/MyBot.py"
	
	where:
		W - width of the map
		H - height of the map



Bot description:

The current strategy of the prototype bot is:

	for every square owned by the bot:
		- if there are any neighbouring squares not owned by the bot which have the strength < square_strength
			move to that neighbour, else stay still
		- if all neighbouring squares are owned by the bot, find the closest square not owned by the bot and move 
			in its direction
	
		
	