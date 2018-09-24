#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for socket(), connect(), send(), recv()
#include <arpa/inet.h>		//for sockaddr_in and inet_addr()
#include <stdlib.h>		//for atoi() and exit()
#include <string.h>		//for memset()
#include <unistd.h>		//for close


void DieWithError(char *errorMessage);	//Error handling function

void TCPClient(int sock);

//Client serverIP serverPort
int main(int argc, char **argv){
	int sock;		//Socket descriptor
	struct sockaddr_in servAddr;	//Server address
	unsigned short servPort;	//Server port
	char *servIP;			//Server IP address


	//Test for correct number
	if(argc != 3){
		fprintf(stderr, "Usage: %s <Server IP> <Server Port>\n", argv[0]);
		exit(1);
	}

	servIP = argv[1];		//First arg : server IP
	servPort = atoi(argv[2]);	//Second arg : server port

		
	//Create a reliable stream socket (TCP)
	if((sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
		DieWithError("socket() failed");

	//Construct the server address structure
	memset(&servAddr, 0, sizeof(servAddr));
	servAddr.sin_family = AF_INET;			//Zero out structure
	servAddr.sin_addr.s_addr = inet_addr(servIP);	//Internet address family
	servAddr.sin_port = htons(servPort);		//Server port

	
	//Establish the connection to the server
	if(connect(sock, (struct sockaddr *) &servAddr, sizeof(servAddr)) < 0)
		DieWithError("connect () failed");


	TCPClient(sock);
	

	close(sock);
	return 0;
}
