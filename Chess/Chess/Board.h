#pragma once
#include "Piece.h"
#include <sstream>

class Board {
private:
	Piece* board[8][8];
	Player *p1, *p2;
	void initBoard();
public:
	int p1_death, p2_death;
	Board(int mode, Player* p1, Player* p2);
	~Board();
	void save();
	void death(Piece* piece);
	void printBoard();
	void play();
	void play(Piece* temp, int i, int j);
	Piece* getPiece(int i, int j);
	Piece* (*getBoard())[8]{
		return board;
	}
	void revive(std::string name, int i, int j, int side);


};