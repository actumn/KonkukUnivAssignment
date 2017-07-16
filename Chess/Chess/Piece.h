#pragma once
#include <string>
#include "Player.h"

class Piece {
private:
	int i, j;
	std::string name;
	Player* player;
	bool moved;
	bool doubleMoved;
	bool passant;
public:
	Piece(int i, int j, std::string name, Player* player);
	~Piece();
	void promotion();
	char getInitial();
	int getI();	int getJ();
	void move(int i, int j);
	bool movable(Piece* board[8][8], int i, int j);
	int getPlayerSide();
	bool hasMoved();
	bool isPassant();
	bool isDoubleMoved();
	bool checkMovable(Piece* board[8][8]);
	std::string getName();
	Player* getPlayer();
};