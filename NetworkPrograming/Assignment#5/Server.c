#include <stdio.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
//Header same with client

#define MAXPENDING 5	//Maximum outstanding connection requests

void DieWithError(char *errorMessage);
void HandleTCPClient(int clntSocket1, int clntSocket2);

//Server servPort
int main(int argc, char *argv[]){
	int servSock;			//Server Socket descriptor
	int clntSock1;			//client 1 Socket descriptor
	int clntSock2;			//client 2
	struct sockaddr_in servAddr;	//Local address
	struct sockaddr_in clntAddr1;	//Client 1 address
	struct sockaddr_in clntAddr2;	//Client 2 address
	unsigned short servPort;	//Server port
	unsigned int clntLen;		//Length of client address data structure

	pid_t processID;

	//Test for correct number of arguments
	if( argc != 2 ){
		fprintf(stderr, "Usage: %s <Server Port> \n", argv[0]);
		exit(1);
	}

	servPort = atoi(argv[1]);	//Frist arg: local port


	//Create socket for incoming connections
	if((servSock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
		DieWithError("socket() failed");


	//Construct local address structure
	memset(&servAddr, 0, sizeof(servAddr));		//Zero out structure
	servAddr.sin_family = AF_INET;			//Internet address family
	servAddr.sin_addr.s_addr = htonl(INADDR_ANY);	//Any incoming interface
	servAddr.sin_port = htons(servPort);		//Local port


	//Bind to the local address
	if(bind(servSock, (struct sockaddr *) &servAddr, sizeof(servAddr)) < 0)
		DieWithError("bind() failed");

	//Mark the socket so it will listen for incoming connections
	if(listen(servSock, MAXPENDING) < 0)
		DieWithError("listen() failed");

	//Run forever
	for(;;)
	{
		//Set the size of the in-out parameter
		clntLen = sizeof(clntAddr1);

		//wait for a client to connect
		if((clntSock1 = accept(servSock, (struct sockaddr *) &clntAddr1, &clntLen)) < 0)
			DieWithError("accept() failed");

		if((clntSock2 = accept(servSock, (struct sockaddr *) &clntAddr2, &clntLen)) < 0)
			DieWithError("accept() failed");


		if((processID = fork()) < 0) DieWithError("fork() failed");
		else if (processID == 0) {
			/* child process */
			//clntSock is connected to a client
			printf("Handling client %s\n", inet_ntoa(clntAddr1.sin_addr));
			printf("Handling client %s\n", inet_ntoa(clntAddr2.sin_addr));
			close(servSock);
			HandleTCPClient(clntSock1, clntSock2);
			printf("Disconnect client %s\n", inet_ntoa(clntAddr1.sin_addr));
			printf("Disconnect client %s\n", inet_ntoa(clntAddr2.sin_addr));
			exit(0);
		}

	}

	//Not reached

	exit(0);

}
