#include <iostream>
#include "Game.h"

int main() {
	int mode;
	do {
		std::cout << "Select mode\n"
			<< "1. replay \n"
			<< "2. Player vs Player \n"
			<< "3. Player vs Cpu \n"
			<< "4. Cpu vs Cpu \n"
			<< "5. exit\n" << std::endl;
			std::cin >> mode;
	} while (mode > 5 || mode < 0);

	Game game(mode);

	while (!game.gameOver()) {
		game.play();
	}
	
	while (1) {
		do {
			std::cout << "Select mode\n"
				<< "1. replay \n"
				<< "2. regame \n"
				<< "3. exit\n" << std::endl;
			std::cin >> mode;
		} while (mode > 3 || mode < 0);
		switch (mode) {
		case 1:
		case 2:
			game.reGame(mode);
			break;
		case 3:
			exit(0);
			break;
		}
		game.reGame(2);
	}
	
	return 0;
}