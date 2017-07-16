#pragma once
#include <list>
#include <string>
#include "record.h"
class Piece;
class Board;
class Player {
private:
	std::list<Record> record;
	int side, is_cpu;
	
public:
	Player(int side);
	~Player();
	bool win;
	int getSide();
	void setIs_cpu(int is_cpu);
	void pushRecord(Piece* piece, int preI, int preJ, int nextI, int nextJ, Piece* death);
	Record popRecord();
	std::list<Record> getRecord();
	bool goBack(Board* gameBoard);
};