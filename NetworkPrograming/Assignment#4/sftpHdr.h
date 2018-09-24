#define FileUpReq 01
#define FileDownReq 02
#define FileSuc 03
#define FileFail 04
#define RLSReq 11
#define RCDReq 12
#define FileAck 13
#define Exit 21
#define RLSdir 22
#define RLSEnd 23
#define RCDSuc 31
#define RCDFail 32

#define BUFSIZE 1024	//size of buffer for up/download

char MsgType;	//check message type
