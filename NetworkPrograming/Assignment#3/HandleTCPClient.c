#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for recv() and send()
#include <unistd.h>		//for close()

#define BUFSIZE 1024		//size of receive buffer
#define FILESIZE 01
#define FILEACK 11
#define SENDFILE 02


void DieWithError(char *errorMessage);	//Error handling function

void HandleTCPClient(int clntSocket)
{
	char msgType;		//check message type
	int fileSize = 0;	//check file size
	char fileBuf[BUFSIZE];	//file buffer
	int recvFileSize = 0;
	FILE *file;
	int bufSize = 0;

	
	/* Recv file size */
	if(recv(clntSocket, &msgType, 1, 0) != 1)
		DieWithError("recv() failed");
	if(msgType != FILESIZE){
		printf("Bad request");
		return;
	}
	recv(clntSocket, &fileSize, sizeof(int), 0);
	printf("fileSize : %d\n", fileSize);


	/* Send ACK Message */
	printf("Send ACK Message\n");
	msgType = FILEACK;
	if(send(clntSocket, &msgType, 1, 0) != 1)
		DieWithError("send() error");



	/* Recv file */
	printf("Recieve File\n");
	file = fopen("fileReceived", "w");
	if(file == NULL)
		DieWithError("File open error");
	if(recv(clntSocket, &msgType, 1, 0) != 1)
		DieWithError("recv() error");
	if(msgType != SENDFILE){
		printf("Bad request");
		return;
	}
	
	bufSize = BUFSIZE;
	while(fileSize > 0){
		if(fileSize < BUFSIZE)
			bufSize = fileSize;

                printf("size test : %d\n", bufSize);

		if((recvFileSize = recv(clntSocket, fileBuf, bufSize, 0)) != bufSize)
			DieWithError("fileBuf recv() Error");
		fileBuf[recvFileSize] = 0;

		fileSize -= recvFileSize;
                printf("size : %d, %d\n", recvFileSize, fileSize);

		fwrite(fileBuf, sizeof(char), bufSize, file);

		recvFileSize = 0;
	}
	fclose(file);

	printf("file receive \n");

        /* send ACK message */
	printf("Send ACK Message\n");
        msgType = FILEACK;
        if(send(clntSocket, &msgType, 1, 0) != 1)
                DieWithError("send() error");

	close(clntSocket);
}
