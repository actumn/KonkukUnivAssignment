#include <iostream>
#include <string>
#include <sstream>
#include <fstream>
#include <list>
#include "Board.h"
#include <Windows.h>


class KeyEvent {
private:
	HANDLE hln;
	COORD KeyWhere;
	DWORD EventCount;
	INPUT_RECORD InRec;
	DWORD NumRead;
	int downKey;
public:
	KeyEvent() {
		hln = GetStdHandle(STD_INPUT_HANDLE);
		EventCount = 1;
	}
	int getKey() {
		ReadConsoleInput(hln, &InRec, 1, &NumRead);
		if (InRec.EventType == KEY_EVENT) {
			if (InRec.Event.KeyEvent.bKeyDown) {
				downKey = InRec.Event.KeyEvent.wVirtualKeyCode;
				return downKey;
			}
			else {
				return -1;
			}
		}
		return -1;
	}

};

Board::Board(int mode, Player* player1, Player* player2) {
	p1 = player1;
	p2 = player2;
	
	initBoard();
	p1_death = 0; p2_death = 0;
}
Board::~Board() {
	for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
			if (board[i][j]) delete board[i][j];
		}
	}
}
void Board::save() {
	std::string fileName = "history.log";
	std::ofstream ofs;
	std::list<Record> p1_record = p1->getRecord();
	std::list<Record> p2_record = p2->getRecord();

	ofs.open(fileName, std::ios::out);
	if (!ofs) { std::cout << "Unable to open file" << std::endl; return; }

	std::list<Record>::iterator it1 = p1_record.begin();
	std::list<Record>::iterator it2 = p2_record.begin();
	while (it1 != p1_record.end() || it2 != p2_record.end()) {

		if (it1 != p1_record.end()) {
			ofs << "1p turn \n"
				<< (*it1).name << " "
				<< (char)((*it1).preI + 48)
				<< (char)((*it1).preJ + 65)
				<< " -> "
				<< (char)((*it1).nextI + 48)
				<< (char)((*it1).nextJ + 65)
				<< std::endl;
			if ((*it1).deadObj != "") {
				ofs << "2p "
					<< (*it1).deadObj
					<< " Down" << std::endl;
			}
			++it1;
		}

		if (it2 != p2_record.end()) {
			ofs << "2p turn \n"
				<< (*it2).name << " "
				<< (char)((*it2).preI + 48)
				<< (char)((*it2).preJ + 65)
				<< " -> "
				<< (char)((*it2).nextI + 48)
				<< (char)((*it2).nextJ + 65)
				<< std::endl;
			if ((*it2).deadObj != "") {
				ofs << "1p "
					<< (*it2).deadObj
					<< " Down" << std::endl;
			}
			++it2;
		}
	}

	ofs << "Game Over" << std::endl;
	if (p1->win) ofs << "1p Win" << std::endl;
	else ofs << "2p Win" << std::endl;

	ofs.close();
}

void Board::death(Piece* piece) {
	if (piece->getPlayerSide() == -1) p1_death++;
	else p2_death++;
	
	delete piece;
}

void Board::play() {
	int keyCode;
	KeyEvent k;

	std::string fileName = "history.log";
	std::ifstream ifs;
	std::string inputString;
	ifs.open(fileName.c_str());

	int preI, preJ;
	int nextI, nextJ;
	while (1) {
		keyCode = k.getKey();
		if (keyCode != ' ') continue; 
		ifs >> inputString;
		std::cout << inputString << std::endl;
		if (inputString == "1p") continue;
		if (('1' < inputString[0] && inputString[0] < '8') && 'A' < inputString[1] || inputString[1] < 'H') {

			preI = (int)inputString[0] - 48;
			preJ = (int)inputString[1] - 65;
			Piece* temp = board[preI][preJ];
	
			std::cout << preI << preJ << std::endl;

			ifs >> inputString;
			std::cout << inputString << std::endl;
			ifs >> inputString;
			std::cout << inputString << std::endl;
			nextI = 8 - ((int)inputString[0] - 48);
			nextJ = (int)inputString[1] - 65;
			std::cout << nextI << nextJ << std::endl;



			if (temp->movable(board, nextI, nextJ)) {
				if (board[nextI][nextJ]) {
					death(board[nextI][nextJ]);
					board[nextI][nextJ] = NULL;
				}
				if (temp->isPassant()) {
					death(board[nextI][nextJ]);
					board[nextI][nextJ] = NULL;
				}
				board[temp->getI()][temp->getJ()] = NULL;
				board[nextI][nextJ] = temp;
				temp->move(nextI, nextJ);

				system("cls");
				printBoard();
			}
		}
	}
}

void Board::play(Piece* temp, int i, int j) {
	temp->getPlayer()->pushRecord(temp, temp->getI(), temp->getJ(), i, j, board[i][j]);
	
	if (board[i][j] != NULL) {
		if (board[i][j]->getName() == "King") {
			temp->getPlayer()->win = true;
			std::cout << temp->getPlayerSide() << "p win" << std::endl;
			save();
		}
		
		death(board[i][j]);
		board[i][j] = NULL;
	}
	
	if (temp->isPassant()) {
		death(board[i - 1][j]);
		board[i - 1][j] = NULL;
	}
	board[temp->getI()][temp->getJ()] = NULL;
	board[i][j] = temp;
	temp->move(i, j);
	
	std::cin >> input;
	
	if ((temp->getI() == 0 && temp->getPlayerSide() == -1) || (temp->getI() == 7) && temp->getPlayerSide() == 1)
		temp->promotion();
}
Piece* Board::getPiece(int i, int j) {
	return board[i][j];
}

void Board::revive(std::string name, int i, int j, int side) {
	if (side == 1)
		board[i][j] = new Piece(i, j, name, p2);
	else
		board[i][j] = new Piece(i, j, name, p1);

}


void Board::printBoard() {
	std::cout << "P1 dead : " << p1_death << " "<< "P2 dead : " << p2_death << '\n' <<std::endl;

	for (int i = 0; i < 8; i++) {
		for (int j = 0; j < 8; j++) {
			if (board[i][j] == NULL) {
				std::cout << ".";
			}
			else {
				std::cout << board[i][j]->getInitial();
			}
			std::cout << " ";
		}
		std::cout << "  " << 8 - i << std::endl;
	}

	std::cout << '\n' << "A B C D E F G H" << std::endl;
}

void Board::initBoard() {
	for(int i = 0; i < 8; i++){
		for(int j = 0; j < 8; j++){
			board[i][j] = NULL;
		}
	}
	board[0][0] = new Piece(0, 0, "Rock", p2);
	board[0][1] = new Piece(0, 1, "Knight", p2);
	board[0][2] = new Piece(0, 2, "Bishop", p2);
	board[0][3] = new Piece(0, 3, "Queen", p2);
	board[0][4] = new Piece(0, 4, "King", p2);
	board[0][5] = new Piece(0, 5, "Bishop", p2);
	board[0][6] = new Piece(0, 6, "Knight", p2);
	board[0][7] = new Piece(0, 7, "Rock", p2);

	board[1][0] = new Piece(1, 0, "Pawn", p2);
	board[1][1] = new Piece(1, 1, "Pawn", p2);
	board[1][2] = new Piece(1, 2, "Pawn", p2);
	board[1][3] = new Piece(1, 3, "Pawn", p2);
	board[1][4] = new Piece(1, 4, "Pawn", p2);
	board[1][5] = new Piece(1, 5, "Pawn", p2);
	board[1][6] = new Piece(1, 6, "Pawn", p2);
	board[1][7] = new Piece(1, 7, "Pawn", p2);



	board[6][0] = new Piece(6, 0, "Pawn", p1);
	board[6][1] = new Piece(6, 1, "Pawn", p1);
	board[6][2] = new Piece(6, 2, "Pawn", p1);
	board[6][3] = new Piece(6, 3, "Pawn", p1);
	board[6][4] = new Piece(6, 4, "Pawn", p1);
	board[6][5] = new Piece(6, 5, "Pawn", p1);
	board[6][6] = new Piece(6, 6, "Pawn", p1);
	board[6][7] = new Piece(6, 7, "Pawn", p1);

	board[7][0] = new Piece(7, 0, "Rock", p1);
	board[7][1] = new Piece(7, 1, "Knight", p1);
	board[7][2] = new Piece(7, 2, "Bishop", p1);
	board[7][3] = new Piece(7, 3, "Queen", p1);
	board[7][4] = new Piece(7, 4, "King", p1);
	board[7][5] = new Piece(7, 5, "Bishop", p1);
	board[7][6] = new Piece(7, 6, "Knight", p1);
	board[7][7] = new Piece(7, 7, "Rock", p1);
}