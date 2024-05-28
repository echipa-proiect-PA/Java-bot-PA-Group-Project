build:
	echo "Python project"

clean:
	rm -rf main_bot/__pycache__


run:
	python main_bot/MyBot.py

%:
	@:

archive1:
	rm -f etapa1.zip
	zip -r etapa1.zip main_bot Makefile README.md
