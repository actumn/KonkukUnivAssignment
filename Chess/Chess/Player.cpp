#include<iostream>
#include "Player.h"
#include "Piece.h"
#include "Board.h"

Player::Player(int side1) {
	side = side1;
	win = false;
}
Player::~Player() {

}

int Player::getSide() {
	return side;
}

void Player::setIs_cpu(int is_cpu1) {
	is_cpu = is_cpu1;
}

void Player::pushRecord(Piece* piece, int preI, int preJ, int nextI, int nextJ, Piece* death) {
	Record* temp = new Record(piece->getName(), preI, preJ, nextI, nextJ, death);
	record.push_back(*temp);
}

Record Player::popRecord() {
	Record temp("", 0, 0, 0, 0, NULL);
	if (!record.empty()) {
		temp = *(--(record.end()));
		record.pop_back();
	}
	return temp;
}

std::list<Record> Player::getRecord() {
	return record;
}

bool Player::goBack(Board* board) {
	Record record = popRecord(); 
	if(record.name != ""){
		board->play(board->getPiece(record.nextI, record.nextJ), record.preI, record.preJ);
		if (record.deadObj != "") {
			board->revive(record.deadObj, record.objI, record.objJ, -side);
		}
		return true;
	}
	return false;
}