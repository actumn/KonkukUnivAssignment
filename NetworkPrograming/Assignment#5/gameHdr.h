#define GAMECOORD 01
#define CHATSTART 11
#define CHATREPLY 12
#define REMATCH 21
#define QUIT 22

#define BUFSIZE 1024	//size of buffer for up/download

char MsgType;		//check message type

struct coordinate {
	short horizontal;
	short vertical;
} coord;

int board[3][3] = {
	{0, 0, 0},
	{0, 0, 0},
	{0, 0, 0}
};

int size = 0;
