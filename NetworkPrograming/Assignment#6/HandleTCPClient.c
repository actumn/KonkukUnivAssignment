#include <stdio.h>		//for printf() and fprintf()
#include <sys/socket.h>		//for recv() and send()
#include <unistd.h>		//for close()
/* for select(), FD_SET(), FD_ZERO(), FD_ISSET()*/
#include <sys/time.h>
#include <sys/types.h>
#include <unistd.h>
#include <string.h>

#define BUFSIZE 1024		//size of receive buffer

void DieWithError(char *errorMessage);	//Error handling function

void HandleTCPClient(int clntSocket)
{
	char line[BUFSIZE], msg[BUFSIZE + 1];
	int size = 0;
	int chat_end = 0;
	fd_set readOK;


	while(1)
	{
		FD_ZERO(&readOK);
		FD_SET(0, &readOK);
		FD_SET(clntSocket, &readOK);
		select(clntSocket+1, (fd_set*)&readOK, NULL, NULL, NULL);

		//send
		if(FD_ISSET(0, &readOK))
		{
			if(fgets(msg, BUFSIZE, stdin))
			{
				sprintf(line, "server> %s", msg);
				send(clntSocket, line, strlen(line), 0);
			}

			if(chat_end) break;
		}
		//recv
		if(FD_ISSET(clntSocket, &readOK))
		{
			int size;
			if((size = recv(clntSocket, msg, BUFSIZE, 0)) > 0)
			{
				msg[size] = '\0';
				printf("%s \n", msg);
			}

			if(chat_end) break;
		}
	}


	close(clntSocket);
}
