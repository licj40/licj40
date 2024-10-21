#ifndef _ESP8266_H_
#define _ESP8266_H_
#include "systick.h"

#define REV_OK		0	//������ɱ�־
#define REV_WAIT	1	//����δ��ɱ�־

#define SSID "YiCeng"
#define WIFI_PASSWORD "xmw66668888"
#define SERVER_ADDR "192.168.0.140"

//#define SERVER_PORT 8088
#define SERVER_PORT 52014


void ESP8266_Init(void);
u8 ESP8266_WaitRecive(void);
void ESP8266_Clear(void);
void ESP8266_SendData(unsigned char *data, unsigned short len);
void ESP8266_ConnecWifi(void);
#endif

