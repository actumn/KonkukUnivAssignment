#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
//Header same with client


#define MAXPENDING 5	//Maximum outstanding connection requests


void DieWithError(char *errorMessage);	//Error handling function
void HandleTCPClient(int clntSocket, char* remoteAddr);	//TCP client handling function


//TCPEchoClient echoServPort 
int main(int argc, char *argv[])
{
	int servSock;			//server Socket descriptor
	int clntSock;			//client Socket descriptor
	struct sockaddr_in echoServAddr;//Local address
	struct sockaddr_in echoClntAddr;//Client address
	unsigned short echoServPort;	//Server port
	unsigned int clntLen;		//Length of client address data structure

	if ( argc != 2 ) {	//Test for correct number of arguments
		fprintf(stderr, "Usage: %s <Server Port> \n", argv[0]);
		exit(1);
	}
	
	echoServPort = atoi(argv[1]);	//First arg: local port

	//Create socket for incoming connections
	if ((servSock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
		DieWithError("socket () failed");


	//Construct local address structure
	memset(&echoServAddr, 0, sizeof(echoServAddr));		//Zero out structure
	echoServAddr.sin_family = AF_INET;			//Internet address family
	echoServAddr.sin_addr.s_addr = htonl(INADDR_ANY);	//Any incoming interface
	echoServAddr.sin_port = htons(echoServPort);		//Local port


	//Bind to the local address
	if (bind(servSock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
		DieWithError("bind() failed");

	//Mark the socket so it will listem for incoming connections
	if (listen(servSock, MAXPENDING) < 0)
		DieWithError("listen() failed");

	//Run forever
	for (;;) 
	{
		//Set the size of the in-out parameter
		clntLen = sizeof(echoClntAddr);

		//Wait for a client to connect
		if ((clntSock = accept(servSock, (struct sockaddr *) &echoClntAddr, &clntLen)) < 0)
			DieWithError("accept() failed");

		//clntSock is connected to a client
		printf("Handling client %s\n", inet_ntoa(echoClntAddr.sin_addr));

		HandleTCPClient(clntSock, inet_ntoa(echoClntAddr.sin_addr));
	}
	//Not Reached

	exit(0);
}
