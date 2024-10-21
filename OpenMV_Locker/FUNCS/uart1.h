#ifndef _UART1_H_
#define _UART1_H_

#include "sys.h"
#define USART1_RXBUFF_SIZE   150 

extern unsigned int RxCounter;          //�ⲿ�����������ļ����Ե��øñ���
extern char Usart1RecBuf[USART1_RXBUFF_SIZE]; //�ⲿ�����������ļ����Ե��øñ���

void UART1_Init(void);

void uart1_SendStr(char*SendBuf);
void uart1_send(unsigned char *bufs,unsigned char len);

u8 uart1_TX_wifimsg(char* tx_msg);

#endif
