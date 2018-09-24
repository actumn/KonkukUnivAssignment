#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for recv() and send()
#include <sys/stat.h>		//for stat()
#include <unistd.h>		//for close()
#include <dirent.h>		//for DIR functions
#include <fcntl.h>		//for fopen()
#include "sftpHdr.h"		//protocol definition header

void recvFile(int clntSocket);
void sendFile(int clntSocket);
void RLSReqF(int clntSocket);
void RCDReqF(int clntSocket);

void DieWithError(char *errorMessage);	//Error handling function

void HandleTCPClient(int clntSocket)
{
	while(1){
		recv(clntSocket, &MsgType, 1, 0);

		if(MsgType == FileUpReq){
			recvFile(clntSocket);
		}
		else if(MsgType == FileDownReq){
			sendFile(clntSocket);
		}
		else if(MsgType == RLSReq){
			RLSReqF(clntSocket);
		}
		else if(MsgType == RCDReq){
			RCDReqF(clntSocket);
		}
		else if(MsgType == Exit){
			break;
		}
		else{
			printf("Unknown MsgType");
		}
	}

}

//recv file from server
void recvFile(int clntSocket){
	char fileBuf[BUFSIZE];
	int fileSize = 0;	//check file size
	int recvFileSize = 0;	//file buffer
	FILE *file;
	int bufSize = 0;
	char fileName[40];

	/* No file Exception */
	recv(clntSocket, &MsgType, 1, 0);
	if(MsgType == FileFail) return;

	/* Recv fileSize */
	recv(clntSocket, &fileSize, sizeof(int), 0);

	/* Recv fileName */
	recv(clntSocket, fileName, sizeof(char) * 40, 0);

	file = fopen(fileName, "w");
	if(file == NULL)
		DieWithError("File open Error\n");

	/* Recv file */
	printf("Receive file\n");

	bufSize = BUFSIZE;
	while(fileSize > 0){
		if(fileSize < BUFSIZE)
			bufSize = fileSize;

		printf("size test : %d\n", bufSize);

		if((recvFileSize = recv(clntSocket, fileBuf, bufSize, 0) != bufSize))
			DieWithError("fileBuf recv() Error\n");
		fileBuf[recvFileSize] = 0;

		fileSize -= recvFileSize;

		fwrite(fileBuf, sizeof(char), bufSize, file);

		recvFileSize = 0;
	}
	fclose(file);

	printf("file receive : %s\n", fileName);

	/* Send ACK Message */
	MsgType = FileAck;
	send(clntSocket, &MsgType, 1, 0);

}

//send file to client
void sendFile(int clntSocket){
	char fileName[40];
	struct stat st;
	FILE *file;
	int fd;
	char fileBuffer[BUFSIZE];

	int sendSize = 0;		//packet size
	int sendTotalSize = 0;		//total size of packet sent
	int fileSize = 0;		//fileSize for sending server
	int fileSize2 = 0;

	/* Recv fileName */
	recv(clntSocket, fileName, sizeof(char) * 40, 0);

	/* No file Exception */
	if((fd = open(fileName, O_RDONLY, 0)) <0){
		MsgType = FileFail;
		send(clntSocket, &MsgType, 1, 0);
		return;
	}
	MsgType = FileSuc;
	send(clntSocket, &MsgType, 1, 0);

	fstat(fd, &st);

	file = fopen(fileName, "r");

	fileSize = st.st_size;

	/* Send file Size */
	send(clntSocket, &fileSize, sizeof(int), 0);

	/* send file */
	printf("file send : %s\n", fileName);
	while(sendTotalSize < fileSize){
		sendSize = fread(fileBuffer, 1, BUFSIZE, file);
		sendTotalSize += sendSize;
		if(send(clntSocket, fileBuffer, sendSize, 0) != sendSize)
			DieWithError("fileBuffer Send() Error\n");
	}

	/* Send ACK Message */
	MsgType = FileAck;
	send(clntSocket, &MsgType, 1, 0);

}

//remote ls by client command
void RLSReqF(int clntSocket){
	DIR* servdir = opendir(".");
	struct dirent *dir_entry;
	char* dirname;

	while(dir_entry = readdir(servdir))
	{
		MsgType = RLSdir;
		send(clntSocket, &MsgType, 1, 0);

		dirname = dir_entry->d_name;
		send(clntSocket, dirname, sizeof(char) * 40, 0);
	}

	MsgType = RLSEnd;
	send(clntSocket, &MsgType, sizeof(char), 0);
}

//remote cd by client command
void RCDReqF(int clntSocket){
	char dirname[40];

	recv(clntSocket, dirname, sizeof(char) * 40, 0);

	if(chdir(dirname))
	{
		MsgType = RCDFail;
	}
	else
	{
		MsgType = RCDSuc;
	}

	send(clntSocket, &MsgType, sizeof(char), 0);
}


