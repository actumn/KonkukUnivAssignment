#include <iostream>
#include <stdlib.h>
#include <Windows.h>
#include "Game.h"


Game::Game(int mode1) {
	mode = mode1;
	turn = 1;
	p1 = new Player(-1); // 1p
	p2 = new Player(1); // 2p
	board = new Board(mode1, p1, p2);
}

void Game::play() {
	switch (mode) {
	case 1:
		play1();
		break;
	case 2:
		play2();
		break;
	case 3:
		play3();
		break;
	case 4:
		play4();
		break;
	case 5:
		exit(0);
		break;
	}
}
void Game::play1() {
	std::cout << "±¸Çö °üµÒ" << std::endl;
	exit(0);
	system("cls");
	board->printBoard();
	board->play();
}
void Game::play3() {
	std::cout << "±¸Çö ¾ÈµÊ" << std::endl;
	exit(0);
}
void Game::play4() {
	std::cout << "±¸Çö ¾ÈµÊ" << std::endl;
	exit(0);
}
void Game::play2() {
	system("cls");
	board->printBoard();

	std::string input;
	std::string str1, str2;
	Player *curPlayer = NULL;
	Player *opposite = NULL;
	Piece* temp = NULL;

	if (turn == 1) {
		curPlayer = p1;
		opposite = p2;
	}
	else {
		curPlayer = p2;
		opposite = p1;
	}

	std::cout << curPlayer->getSide() << "p turn" << std::endl;
	do {
		std::cout << "Select piece to move / rollback" << std::endl;
		std::cin >> str1;
		if (str1 == "rollback") {
			if (opposite->goBack(board)) {
				turn = -turn;
				return;
			}
			else continue;
			
		}
		if (str1.size() != 2) continue;
		if (('1' > str1[0] || str1[0] > '8') || 'A' > str1[1] || str1[1] > 'H') continue;

		temp = board->getPiece(8-((int)str1[0] - 48), (int)str1[1] - 65);
		if (temp == NULL) continue;
		if (temp->getPlayerSide() == curPlayer->getSide() && temp->checkMovable(board->getBoard())) break;
	} while (1);
	std::cout << temp->getName() << std::endl;
	do{
		std::cout << "Where to move" << std::endl;
		std::cin >> str2;
		if (str2.size() != 2) continue;
		if (('1' > str2[0] || str2[0] > '8') || 'A' > str2[1] || str2[1] > 'H') continue;

		if (temp->movable(board->getBoard(), 8 - ((int)str2[0] - 48), (int)str2[1] - 65)) break;
	} while (1);
	board->play(temp, 8 - ((int)str2[0] - 48), (int)str2[1] - 65);

	turn = -turn;
}

bool Game::gameOver() {
	return p1->win || p2->win;
}

void Game::reGame(int mode1) {
	delete board;
	delete p1;
	delete p2;

	mode = mode1;
	turn = 1;
	p1 = new Player(-1); // 1p
	p2 = new Player(1); // 2p
	board = new Board(mode1, p1, p2);
}