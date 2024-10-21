#ifndef _UART1_H_
#define _UART1_H_

#include "sys.h"
#define USART1_RXBUFF_SIZE   150 

extern unsigned int RxCounter;          //外部声明，其他文件可以调用该变量
extern char Usart1RecBuf[USART1_RXBUFF_SIZE]; //外部声明，其他文件可以调用该变量

void UART1_Init(void);

void uart1_SendStr(char*SendBuf);
void uart1_send(unsigned char *bufs,unsigned char len);

u8 uart1_TX_wifimsg(char* tx_msg);

#endif
