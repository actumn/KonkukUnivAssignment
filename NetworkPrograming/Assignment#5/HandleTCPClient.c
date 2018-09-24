#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for recv() and send()
#include <sys/stat.h>		//for stat()
#include <unistd.h>		//for close()
#include <string.h>		//for memset()

#include "gameHdr.h"		//protocol definition header

void DieWithError(char *errorMessage);	//Error handling function

void updateGame(int code);
void playGame(int clntSocket1, int clntSocket2);
int check();

void HandleTCPClient(int clntSocket1, int clntSocket2)
{
	char MsgType1, MsgType2;

	while(1){
		playGame(clntSocket1, clntSocket2);	//control two client

		recv(clntSocket1, &MsgType1, 1, 0);	//rematch or quit
		recv(clntSocket2, &MsgType2, 1, 0);

		if(MsgType1 == REMATCH && MsgType2 == REMATCH){
			MsgType = REMATCH;
			send(clntSocket1, &MsgType, 1, 0);
			send(clntSocket2, &MsgType, 1, 0);
			continue;
		}
		else
			break;
	}
}


//update board
void updateGame(int code){
	int horizontal = coord.horizontal;
	int vertical = coord.vertical;

	board[horizontal][vertical] = code;
	size++;
}

//control two client
void playGame(int clntSocket1, int clntSocket2){
	memset(board, 0, sizeof(board));
	size = 0;


	MsgType = GAMECOORD;
	send(clntSocket1, &MsgType, 1, 0);
	send(clntSocket2, &MsgType, 1, 0);

	MsgType = CHATSTART;
	send(clntSocket1, &MsgType, 1, 0);
	MsgType = CHATREPLY;
	send(clntSocket2, &MsgType, 1, 0);

	while(1){
		recv(clntSocket1, &coord, sizeof(coord), 0);
		send(clntSocket2, &coord, sizeof(coord), 0);
		updateGame(1);
		if(check())
			break;

		recv(clntSocket2, &coord, sizeof(coord), 0);
		send(clntSocket1, &coord, sizeof(coord), 0);
		updateGame(-1);
		if(check())
			break;
	}

}


//check game
int check(){
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

		if(sum1 == 3 || sum2 == 3 ||
			sum1 == -3 || sum2 == -3)
			return 1;
	}
	sum1 = board[0][0] + board[1][1] + board[2][2];
	sum2 = board[2][0] + board[1][1] + board[0][2];

	if(sum1 == 3 || sum2 == 3 ||
		sum1 == -3 || sum2 == -3)
		return 1;

	if(size > 8)
		return 1;

	return 0;
}
