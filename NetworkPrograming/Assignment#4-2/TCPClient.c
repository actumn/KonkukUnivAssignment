#include <stdio.h>		//for printf() and scanf()
#include <sys/socket.h>		//for recv() and send()
#include <sys/stat.h>		//for stat()
#include <unistd.h>		//for close()
#include <dirent.h>		//for DIR functions
#include <fcntl.h>		//for fopen()
#include "sftpHdr.h"		//protocol definition header

void DiewWithError(char *errorMessage);

void putFile(int clntSocket);
void getFile(int clntSocket);
void ls();
void rls(int clntSocket);
void cd();
void rcd(int clntSocket);


void TCPClient(int clntSocket){
	char msgType;
	int fileSize = 0;
	char cmd = '\n';			//client command

	printf("Welcome to Simple FTP client\n");

	//end by exit command
	while(1){
		printf("p)put g)get l)ls r)rls c)cd d)rcd e)xit\n");
		scanf("%c", &cmd);

		if(cmd == 'p')
		{
			MsgType = FileUpReq;
			send(clntSocket, &MsgType, 1, 0);

			putFile(clntSocket);
		}
		else if(cmd == 'g')
		{
			MsgType = FileDownReq;
			send(clntSocket, &MsgType, 1, 0);

			getFile(clntSocket);
		}
		else if(cmd == 'l')
		{
				ls();
		}
		else if(cmd == 'r')
		{
			MsgType = RLSReq;
			send(clntSocket, &MsgType, 1, 0);

			rls(clntSocket);
		}
		else if(cmd == 'c')
		{
			cd();
		}
		else if(cmd == 'd')
		{
			MsgType = RCDReq;
			send(clntSocket, &MsgType, 1, 0);

			rcd(clntSocket);
		}
		else if(cmd == 'e')
		{
			MsgType = Exit;
			send(clntSocket, &MsgType, 1, 0);

			close(clntSocket);
			DieWithError("Exit by command e)xit\n");
		}
		else
		{

		}

	}
}



//upload file to server
void putFile(int clntSocket){
	char fileName[40];
	struct stat st;
	FILE *file;
	int fd;
	char fileBuffer[BUFSIZE];

	int sendSize = 0;		//packet size
	int sendTotalSize = 0;		//total size of packet sent
	int fileSize = 0;		//fileSize for sending server
	int filesize2 = 0;

	int asciiGrap = 0;

	printf("file name to put to server :");
	scanf("%s", fileName);

	/* No file Excpetion */
	if((fd = open(fileName, O_RDONLY, 0)) < 0){
		printf("No such file");
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

	/* Send fileName */
	send(clntSocket, fileName, sizeof(char) * 40, 0);

	/* Send file */
	printf("file send : %s\n", fileName);
	printf("Sending => ");
	while(sendTotalSize < fileSize){
		sendSize = fread(fileBuffer, 1, BUFSIZE, file);
		sendTotalSize += sendSize;

		if(send(clntSocket, fileBuffer, sendSize, 0) != sendSize)
			DieWithError("fileBuffer Send() Error\n");

		asciiGrap += sendSize;
		if(asciiGrap > fileSize / 10){
			printf("#");
			asciiGrap -= fileSize / 10;
		}
	}

	fclose(file);

	/* Recv ACK Message */
	recv(clntSocket, &MsgType, 1, 0);
	if(MsgType != FileAck)
		DieWithError("\nMsgType Error\n");


	printf("\n%s(%d bytes) uploading success\n", fileName, fileSize);

}

//download file from server
void getFile(int clntSocket){
	char fileBuf[BUFSIZE];
	int fileSize = 0;
	int recvFileSize = 0;	//file buffer
	FILE * file;
	int bufSize = 0;
	char fileName[40];

	int asciiCheck = 0;
	int asciiGrap = 0;

	printf("file name to get from server : ");
	scanf("%s", fileName);

	/* Send fileName */
	send(clntSocket, fileName, sizeof(char) * 40, 0);

	/* No file Exception */
	recv(clntSocket, &MsgType, 1, 0);
	if(MsgType == FileFail)
	{
		printf("No such file");
		return;
	}

	/* Recv fileSize */
	recv(clntSocket, &fileSize, sizeof(int), 0);

	asciiCheck = fileSize / 10;

	/* Recv file */
	printf("Receive file\n");
	file = fopen(fileName, "w");
	if(file == NULL)
		DieWithError("File open Error\n");

	bufSize = BUFSIZE;
	printf("Receiving => ");
	while(fileSize > 0){
		if(fileSize < BUFSIZE)
			bufSize = fileSize;

		if((recvFileSize = recv(clntSocket, fileBuf, bufSize, 0)) != bufSize)
			DieWithError("fileBuf recv() Error\n");

		fileBuf[recvFileSize] = 0;
		fileSize -= recvFileSize;

		fwrite(fileBuf, sizeof(char), bufSize, file);

		asciiGrap += recvFileSize;
		if(asciiGrap > asciiCheck){
			printf("#");
			asciiGrap -= asciiCheck;
		}

		recvFileSize = 0;

	}
	fclose(file);

	printf("\nfile receive : %s\n", fileName);

	/* Send ACK Message */
	recv(clntSocket, &MsgType, 1, 0);
	if(MsgType != FileAck)
		DieWithError("MsgType Error\n");

}

//call directory
void ls(){
	DIR *dir;
	struct dirent *dir_entry;

	dir = opendir(".");

	while(dir_entry = readdir(dir))
	{
		printf("%s \n", dir_entry->d_name);
	}

	seekdir(dir, 0);
}

//call remote directory
void rls(int clntSocket){
	char dirname[40];

	while(1){
		recv(clntSocket, &MsgType, 1, 0);
		if(MsgType == RLSEnd)
			break;

		recv(clntSocket, dirname, sizeof(char) * 40, 0);
		printf("%s \n", dirname);
	}

}

//cd command
void cd(){
	char dirname[40];

	printf("input directory name to cd\n");
	scanf("%s", dirname);
	if(chdir(dirname))
	{
		printf("no such directory\n");
	}

}

//rcd command
void rcd(int clntSocket){
	char dirname[40];

	printf("input directory name to rcd\n");
	scanf("%s", dirname);

	send(clntSocket, dirname, sizeof(char) * 40, 0);

	recv(clntSocket, &MsgType, sizeof(char), 0);

	if(MsgType == RCDFail)
	{
		printf("no such directory\n");
	}

}
