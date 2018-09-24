#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for recv() and send()
#include <unistd.h>		//for close()
#include <stdlib.h>
#include <string.h>

#define RCVBUFSIZE 32		//size of receive buffer

void DieWithError(char *errorMessage);	//Error handling function

void HandleTCPClient(int clntSocket, char* remoteAddr)
{
	char echoBuffer[RCVBUFSIZE];	//Buffer for echo string
	int recvMsgSize;		//Size of received message

	while(1){
		//Receive message from client
		if ((recvMsgSize = recv(clntSocket, echoBuffer, RCVBUFSIZE, 0)) < 0)
			DieWithError("recv() failed");
		
		echoBuffer[recvMsgSize] = '\0';
		printf("From %s msg : %s\n", remoteAddr, echoBuffer);


		printf("To %s msg : ", remoteAddr);
		scanf("%s", echoBuffer);

		if(!strcmp(echoBuffer, "/quit")){
			DieWithError("exit by /quit");
		}

		//Send received string and receive again until end of transmission
		//Echo message back to client
		if (send(clntSocket, echoBuffer, recvMsgSize, 0) != recvMsgSize)
			DieWithError("send() failed");
		
	}

	close(clntSocket);	//close client socket
}
