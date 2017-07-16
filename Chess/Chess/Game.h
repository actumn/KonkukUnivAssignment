#pragma once
#include "Board.h"

class Game {
private:
	int mode, turn;
	Board* board;
	Player *p1, *p2;
public:
	Game(int mode);
	void reGame(int mode1);
	void play();
	void play1();
	void play2();
	void play3();
	void play4();
	bool gameOver();
};