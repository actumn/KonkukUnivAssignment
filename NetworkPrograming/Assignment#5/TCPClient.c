#include <stdio.h>		//for printf() and scanf()
#include <sys/socket.h>		//for recv() and send()
#include <sys/stat.h>		//for stat()
#include <unistd.h>		//for close()
#include <string.h>		//for memset()

#include "gameHdr.h"		//protocol definition header

void DiewWithError(char *errorMessage);

void getcoord();		//for user input coord
void updateGame(int code);	//game board update
void printboard();		//for draw board
void playGame(int clntSocket);	//for game
int check(int playerCode);

void TCPClient(int clntSocket){
	char cmd = '\n';

	while(1){
		//match with opponent
		playGame(clntSocket);

		//After end of game
		printf("r)Rematch q)Quit\n");
		getchar();
		scanf("%c", &cmd);

		if(cmd == 'r')
		{
			MsgType = REMATCH;
			send(clntSocket, &MsgType, 1, 0);

			printf("Wait for opponent ..\n");

			recv(clntSocket, &MsgType, 1, 0);
			if(MsgType == REMATCH)
				continue;
			else if(MsgType == QUIT)
				break;
			else
				DieWithError("NOT REMATCH OR QUIT ERROR\n");
		}
		else if(cmd == 'q')
			break;
		else
			DieWithError("cmd Error");
	}
}

void getcoord(){
	int horizontal = 0;
	int vertical = 0;

	while(1){
		printf("Input horizontal and vertical\n");
		scanf("%d %d", &horizontal, &vertical);

		if(board[horizontal][vertical] == 0)
			break;
		if(horizontal > 2 || vertical > 2){
			printf("too much input\n");
			continue;
		}
		printf("Already there's coord\n");
	}

	coord.horizontal = horizontal;
	coord.vertical = vertical;
}

void updateGame(int code){
	int horizontal = coord.horizontal;
	int vertical = coord.vertical;

	board[horizontal][vertical] = code;
	size++;
}

void printboard(){
	int i = 0;
	int j = 0;
	for(i = 0; i < 3; i++){
		for(j = 0; j < 3; j++){
			switch(board[i][j]){
			case 0:
				printf("  ");
				break;
			case 1:
				printf("O ");
				break;
			case -1:
				printf("X ");
				break;
			}
		}

		printf("\n");
	}
}

void playGame(int clntSocket){
	int playerCode = 1;

	memset(board, 0, sizeof(board));
	size = 0;


	printf("wait for opponent to match with... \n");

	recv(clntSocket, &MsgType, 1, 0);
	if(MsgType != GAMECOORD)
		DieWithError("NOT CHATSTART Error\n");

	recv(clntSocket, &MsgType, 1, 0);	//check for reply or wait
	if(MsgType == CHATREPLY){
		printf("\e[1;1H\e[2J");

		printf("wait for opponent ..\n");
		recv(clntSocket, &coord, sizeof(coord), 0);
		playerCode = -1;
		updateGame(-playerCode);
	}
	else if(MsgType != CHATSTART)
		DieWithError("NOT CHATREPLY Error\n");

	while(1){
		printf("\e[1;1H\e[2J");

		printboard();
		getcoord();
		send(clntSocket, &coord, sizeof(coord), 0);
		updateGame(playerCode);
		if(check(playerCode))
			break;
		printf("wait for opponent..\n");
		recv(clntSocket, &coord, sizeof(coord), 0);
		updateGame(-playerCode);
		if(check(playerCode))
			break;
	}
}

int check(int playerCode){
	int sum1 = 0;
	int sum2 = 0;

	int i = 0;
	int j = 0;
	for(i = 0; i < 3; i++){
		sum1 = 0;
		sum2 = 0;

		for(j = 0; j < 3; j++){
			sum1 += board[i][j];
			sum2 += board[j][i];
		}

		if(sum1 == 3 || sum2 == 3){
			if(playerCode == 1)
				printf("Win\n");
			else
				printf("Lose\n");

			return 1;
		}
		else if(sum1 == -3 || sum2 == -3){
			if(playerCode == 1)
				printf("Lose\n");
			else
				printf("Win\n");

			return 1;
		}
	}
	sum1 = board[0][0] + board[1][1] + board[2][2];
	sum2 = board[2][0] + board[1][1] + board[0][2];

	if(sum1 == 3 || sum2 == 3){
		if(playerCode == 1)
			printf("win\n");
		else
			printf("Lose\n");

		return 1;
	}
	else if(sum1 == -3 || sum2 == -3){
		if(playerCode == 1)
			printf("Lose\n");
		else
			printf("Win\n");

		return 1;
	}

	if(size > 8){
		printf("draw\n");
		return 1;
	}

	return 0;
}
