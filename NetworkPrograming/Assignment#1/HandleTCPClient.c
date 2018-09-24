#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for recv() and send()
#include <unistd.h>		//for close()

#define RCVBUFSIZE 32		//size of receive buffer

void DieWithError(char *errorMessage);	//Error handling function

void HandleTCPClient(int clntSocket)
{
	char echoBuffer[RCVBUFSIZE];	//Buffer for echo string
	int recvMsgSize;		//Size of received message
	FILE *log_fp;

	//Receive message from client
	if ((recvMsgSize = recv(clntSocket, echoBuffer, RCVBUFSIZE, 0)) < 0)
		DieWithError("recv() failed");

	log_fp = fopen("echo_history.log","a+");
	echoBuffer[recvMsgSize] = '\0';		//keep tally of total bytes
	printf("%s\n", echoBuffer);		//print the echo buffer
	fprintf(log_fp, "%s\n", echoBuffer);	//write file
	
	//Send received string and receive again until end of transmission
	while (recvMsgSize > 0)	//zero indicates end of transmission
	{		
		
		//Echo message back to client
		if (send(clntSocket, echoBuffer, recvMsgSize, 0) != recvMsgSize)
			DieWithError("send() failed");
		//See if there is more data to receive
		if ((recvMsgSize = recv(clntSocket, echoBuffer, RCVBUFSIZE, 0)) < 0)
			DieWithError("recv() failed");

	}

	close(clntSocket);	//close client socket
	fclose(log_fp);		//close file
	
}
