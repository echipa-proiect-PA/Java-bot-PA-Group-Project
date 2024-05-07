build:
	echo "Python project"

clean:
	rm -rf main_bot/__pycache__


run:
	./game_binaries/halite -d "$(word 2,$(MAKECMDGOALS)) $(word 3,$(MAKECMDGOALS))" -n 1 -s 42 "python3 main_bot/MyBot.py"

%:
	@:

archive1:
	rm -f etapa1.zip
	zip -r etapa1.zip main_bot Makefile README.md
