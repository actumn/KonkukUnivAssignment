#include <iostream>
#include "Piece.h"


Piece::Piece(int i1, int j1, std::string name1, Player* player1) {
	i = i1; j = j1;
	name = name1;
	player = player1;
	moved = false;
	passant = false;
	doubleMoved = false;
}

Piece::~Piece() {

}
void Piece::promotion() {
	std::string input;
	std::cout << "Select Piece : Pawn, Rock, Knight, Bishop, Queen" << std::endl;
	do {
		std::cin >> input;
	} while (input != "Pawn" && input != "Rock" && input == "Knight" && input == "Bishop" && input == "Queen");
	name = input;
}
char Piece::getInitial() {
	char initial;
	if (name == "Knight")
		initial = name[1];
	else
		initial = name[0];

	if (player->getSide() == -1)
		initial = toupper(initial);
	else
		initial = tolower(initial);

	return initial;
}

int Piece::getI() {
	return i;
}
int Piece::getJ() {
	return j;
}

void Piece::move(int i, int j) {
	this->i = i; this->j = j;
	moved = true;
}
bool Piece::movable(Piece* board[8][8], int i, int j) {
	Piece* temp = board[i][j];
	if (i == getI() && j == getJ()) return false;
	if (temp != NULL) 
		if(temp->getPlayerSide() == getPlayerSide()) return false;

	if (name == "Pawn") {
		int forward = getPlayerSide();
		
		if (temp == NULL && i == getI() + forward && j == getJ()) {
			doubleMoved = false;
			return true;
		}
		if (!hasMoved() && temp == NULL && i == getI() + 2 * forward && j == getJ()) {
			doubleMoved = true;
			return true;
		}
		if (board[i][j] && i == getI() + forward && abs(j - getJ()) == 1) return true;

		if (board[i - forward][j] && i == getI() +forward && abs(j-getJ()) == 1 ) {
			Piece* objectPassant = board[i - forward][j];
			if (objectPassant->getPlayerSide() != getPlayerSide() && objectPassant->isDoubleMoved()) {
				passant = true;
				return true;
			}
		}
	}
	else if (name == "Knight") {
		int i_diff = abs(i - getI());
		int j_diff = abs(j - getJ());
		if (i_diff == 2 && j_diff == 1) return true;
		if (i_diff == 1 && j_diff == 2) return true;
	}
	else if (name == "Rock") {
		int i_diff = i - getI();
		int j_diff = j - getJ();
		if (i_diff == 0) {
			if (j_diff > 0) {
				for (int s = 1; s != j_diff; s++)
					if (board[getI()][getJ() + s]) { 
						return false; 
					}
			}
			else if (j_diff < 0) {
				for (int s = -1; s != j_diff; s--)
					if (board[getI()][getJ() + s]) {
						return false;
					}
			}
			return true;
		}
		else if (j_diff == 0) {
			if (i_diff > 0) {
				for (int s = 1; s != i_diff; s++)
					if (board[getI()+s][getJ()]) {
						return false;
					}
			}
			else if (i_diff < 0) {
				for (int s = -1; s != i_diff; s--)
					if (board[getI()+s][getJ()]) {
						return false;
					}
			}
			return true;
		}
	}
	else if (name == "Bishop") {
		int i_diff = i - getI();
		int j_diff = j - getJ();
		if (abs(i_diff) == abs(j_diff)) {
			if (i_diff < 0 && j_diff < 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() + s][getJ() + s]) return false;
				}
			}
			else if (i_diff < 0 && j_diff > 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() + s][getJ() - s]) return false;
				}
			}
			else if (i_diff > 0 && j_diff < 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() - s][getJ() + s]) return false;
				}
			}
			else if (i_diff > 0 && j_diff > 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() - s][getJ() - s]) return false;
				}
			}
			return true;
		}
	}
	else if (name == "Queen") {
		int i_diff = i - getI();
		int j_diff = j - getJ();
		if (i_diff == 0) {
			if (j_diff > 0) {
				for (int s = 1; s != j_diff; s++)
					if (board[getI()][getJ() + s]) {
						return false;
					}
			}
			else if (j_diff < 0) {
				for (int s = -1; s != j_diff; s--)
					if (board[getI()][getJ() + s]) {
						return false;
					}
			}
			return true;
		}
		else if (j_diff == 0) {
			if (i_diff > 0) {
				for (int s = 1; s != i_diff; s++)
					if (board[getI() + s][getJ()]) {
						return false;
					}
			}
			else if (j_diff < 0) {
				for (int s = -1; s != i_diff; s--)
					if (board[getI() + s][getJ()]) {
						return false;
					}
			}
			return true;
		}

		if (abs(i_diff) == abs(j_diff)) {
			if (i_diff < 0 && j_diff < 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() + s][getJ() + s]) return false;
				}
			}
			else if (i_diff < 0 && j_diff > 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() + s][getJ() - s]) return false;
				}
			}
			else if (i_diff > 0 && j_diff < 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() - s][getJ() + s]) return false;
				}
			}
			else if (i_diff > 0 && j_diff > 0) {
				for (int s = -1; s != i_diff; s--) {
					if (board[getI() - s][getJ() - s]) return false;
				}
			}
			return true;
		}
	}
	else if (name == "King") {
		int i_diff = abs(i - getI());
		int j_diff = abs(j - getJ());
		if (i_diff <= 1 && j_diff <= 1) return true;


	}
	return false;
}

int Piece::getPlayerSide() {
	return player->getSide();
}

bool Piece::hasMoved() {
	return moved;
}

std::string Piece::getName() {
	return name;
}

bool Piece::checkMovable(Piece* board[8][8]) {
	for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
			if (movable(board, i, j))
				return true;
		}
	}
	std::cout << "can't move" << std::endl;
	return false;
}

Player* Piece::getPlayer() {
	return player;
}

bool Piece::isPassant() {
	return passant;
}

bool Piece::isDoubleMoved() {
	return doubleMoved;
}