#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for socket(), connect(), send(), recv()
#include <sys/stat.h>		//for stat()
#include <arpa/inet.h>		//for sockaddr_in and inet_addr()
#include <stdlib.h>		//for atoi() and exit()
#include <string.h>		//for memset()
#include <unistd.h>		//for close
#include <fcntl.h>		//for fopen()

#define BUFSIZE 1024
#define FILESIZE 01
#define FILEACK 11
#define SENDFILE 02

void DieWithError(char *errorMessage);	//Error handling function

//Client serverIP serverPort
int main(int argc, char **argv){
	int sock;		//Socket descriptor
	int fd;
	struct sockaddr_in servAddr;	//Server address
	unsigned short servPort;	//Server port
	char *servIP;			//Server IP address
	char *fileName;			//file name
	FILE *file;			//file for send
	char fileBuffer[BUFSIZE];	//Buffer for file
	struct stat st;
	int sendSize = 0;			//packet size
	int sendTotalSize = 0;		//total size of packet sent
	int fileSize = 0;			//fileSize for sending server
	int filesize2 = 0;
	char msgType = 0;			//message type


	fileName = "Canon.mp3";

	if ( (fd = open(fileName, O_RDONLY, 0)) < 0 )
		DieWithError("cannot find fileName");

	fstat(fd, &st);

	file = fopen(fileName, "r");

	printf("%ld\n", st.st_size);


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

	fileSize = st.st_size;		//input file size

	
	
	/* Send file size */
	msgType = FILESIZE;
	if(send(sock, &msgType, 1, 0) != 1)
		DieWithError("send() error");
	if(send(sock, &fileSize, sizeof(int), 0) != sizeof(int))
		DieWithError("send() error");



	/* Recv ACK Message */
	if(recv(sock, &msgType, 1, 0) != 1)
		DieWithError("recv() error");
	if(msgType != FILEACK)
		DieWithError("Bad request");
	printf("receive ACK\n");


	/* Send file */
	printf("file send : %s\n", fileName);
	msgType = SENDFILE;
	if(send(sock, &msgType, 1, 0) != 1)
		DieWithError("send() error");

	while(sendTotalSize < fileSize){
                printf("Test 1 : %d %d\n", sendTotalSize, fileSize);
		sendSize = fread(fileBuffer, 1, BUFSIZE, file);
		sendTotalSize += sendSize;
                printf("Test 2 : %d %d\n", sendTotalSize, fileSize);

		if(send(sock, fileBuffer, sendSize, 0) != sendSize)
			DieWithError("Send() Error");
		
	}
	printf("file send success\n");
        fclose(file);

        /* Recv ACK Message */
        if(recv(sock, &msgType, 1, 0) != 1)
                DieWithError("recv() error");
        if(msgType != FILEACK)
                DieWithError("Bad request");
        printf("receive ACK\n");

	close(sock);
	return 0;
}
