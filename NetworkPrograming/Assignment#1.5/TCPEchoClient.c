#include <stdio.h>			//for printf() and fprintf()
#include <sys/socket.h>			//for socket(), connect(), send(), recv()
#include <arpa/inet.h>			//for sockaddr_in and inet_addr()
#include <stdlib.h>			//for atoi() and exit()
#include <string.h>			//for memset()
#include <unistd.h>			//for close()

#define RCVBUFSIZE 32			//size of receive buffer

void DieWithError(char *errorMessage);	//Error handling function

//TCPEchoClient serverIP echoString
//TCPEchoClient serverIP echoString echoServPort
int main()
{
	int sock;				//Socket descriptor
	struct sockaddr_in echoServAddr;	//Echo server address
	int echoServPort;			//Echo server port
	char *servIP;				//Server IP address
	char *echoString;			//String to send to echo server
	char echoBuffer[RCVBUFSIZE];		//Buffer for echo string
	unsigned int echoStringLen;		//Length of string to echo
	int bytesRcvd;		//Bytes read in single recv() and total bytes read


	servIP = (char*)malloc(sizeof(char) * 15);
	echoString = (char*)malloc(sizeof(char) * RCVBUFSIZE);

	//First input : server ip
	printf("Input server ip:");
	scanf("%s", servIP);
	
	//Second input : server port
	printf("Input server port:");
	scanf("%d", &echoServPort);


	//Create a reliable, stream socket using TCP
	if ((sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
		DieWithError("socket() failed");


	//Construct the server address structure
	memset(&echoServAddr, 0, sizeof(echoServAddr));		//Zero out structure
	echoServAddr.sin_family		= AF_INET;		//Internet address family
	echoServAddr.sin_addr.s_addr	= inet_addr(servIP);	//Server IP address
	echoServAddr.sin_port		= htons(echoServPort);	//Server port


	//Establish the connection to the echo server
	if(connect(sock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
		DieWithError("connect () failed");

	while(1){
		printf("client msg : ");
		scanf("%s", echoString);
		echoStringLen = strlen(echoString);	//Determine input length
		echoBuffer[echoStringLen] = '\0';

		if(!strcmp(echoString,"/quit")){
			DieWithError("exit by /quit");
		}

	
		//Send the string to the server
		if (send(sock, echoString, echoStringLen, 0) != echoStringLen)
			DieWithError("send() sent a different number of bytes than expected");

		//Receive the same string back from the server
		printf("Received: ");			//Setup to print the echoed string
	
		//Receive up to the buffer size (minus 1 to leave space for a null terminator) bytes from the sender
		if ((bytesRcvd = recv(sock, echoBuffer, RCVBUFSIZE - 1, 0)) <= 0)
			DieWithError("recv() failed or connection closed prematurely");
	
		echoBuffer[bytesRcvd] = '\0';	//Terminate the string!
		printf("%s", echoBuffer);	//print the echo buffer
	

		printf("\n");	//print a final linefeed
	}
	close(sock);
	exit(0);
}

