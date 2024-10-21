#include "esp8266.h"
#include "uart1.h"
#include "stdio.h"
#include "string.h"


#define  ESP8266_BUF         Usart1RecBuf 
#define  ESP8266_CNT         RxCounter
#define  STM32_RX1BUFF_SIZE  USART1_RXBUFF_SIZE

unsigned short esp8266_cntPre = 0;


void ESP8266_Clear(void)
{
	memset(ESP8266_BUF, 0, sizeof(ESP8266_BUF));
	ESP8266_CNT = 0;
}


u8 ESP8266_WaitRecive(void)
{
	if(ESP8266_CNT == 0) 							//������ռ���Ϊ0 ��˵��û�д��ڽ��������У�����ֱ����������������
		return REV_WAIT;
		
	if(ESP8266_CNT == esp8266_cntPre)				//�����һ�ε�ֵ�������ͬ����˵���������
	{
		ESP8266_CNT = 0;							//��0���ռ���
			
		return REV_OK;								//���ؽ�����ɱ�־
	}
		
	esp8266_cntPre = ESP8266_CNT;					//��Ϊ��ͬ
	
	return REV_WAIT;								//���ؽ���δ��ɱ�־
}


u8 ESP8266_SendCmd(char *cmd, char *res, u16 time)
{	
  uart1_send((unsigned char *)cmd,strlen((const char *)cmd));
	
	while(time--)
	{
		if(ESP8266_WaitRecive() == REV_OK)							//����յ�����
		{
			if(strstr((const char *)ESP8266_BUF, res) != NULL)		//����������ؼ���
			{
				//printf("%s\n",ESP8266_BUF);
				ESP8266_Clear();									//��ջ���
				
				return 0;
			}
		}
		
		delay_ms(1);
	}
	
	return 1;

}


void ESP8266_SendData(unsigned char *data, unsigned short len)
{

	char cmdBuf[32];
	
	ESP8266_Clear();								//��ս��ջ���
	sprintf(cmdBuf, "AT+CIPSEND=0,%d\r\n", len);		//��������
	if(!ESP8266_SendCmd(cmdBuf, ">", 200))				//�յ���>��ʱ���Է�������
	{
			uart1_send(data , len);         //�����豸������������
	}
}

void ESP8266_ConnecWifi(void)
{
	ESP8266_SendCmd("+++", "OK", 200);
	//rst
	ESP8266_Clear();
	short timeout = 50;
	while(timeout > 0)
	{
		if(!ESP8266_SendCmd("AT+RST\r\n", "OK", 200)) break;
		timeout--; 
		delay_ms(200);
	}
	if(timeout <= 0)
	{
//		printf("reset failed\n");
		return;
	}
//	printf("reset OK\n");
	delay_ms(500);
	
	//station mode
	ESP8266_Clear();
	timeout = 50;
	while(timeout > 0)
	{
		if(!ESP8266_SendCmd("AT+CWMODE=1\r\n", "OK", 200)) break;
		timeout--; 
		delay_ms(100);
	}
	if(timeout <= 0)
	{
//		printf("mode set failed\n");
		return;
	}
//	printf("station mod ok\n");
	delay_ms(500);
	
	//no auto conn access point
	ESP8266_Clear();
	timeout = 50;
	while(timeout > 0)
	{
		if(!ESP8266_SendCmd("AT+CWAUTOCONN=0\r\n", "OK", 200)) break;
		timeout--; 
		delay_ms(100);
	}
	if(timeout <= 0)
	{
//		printf("auto conn set failed\n");
		return;
	}
//	printf("set no auto conn\n");
	delay_ms(500);
	
	//conn ap or wifi
	ESP8266_Clear();
	timeout = 20;
	char buff[128] = {0};
	sprintf(buff,"AT+CWJAP=\"%s\",\"%s\"\r\n",SSID,WIFI_PASSWORD);
	while(ESP8266_SendCmd(buff, "OK", 500))
	{
		delay_ms(200);
	}
//	printf("joined ap OK\n");
	delay_ms(500);

	//set singel connection
	ESP8266_Clear();
	timeout = 150;
	while(timeout > 0)
	{
		if(!ESP8266_SendCmd("AT+CIPMUX=0\r\n", "OK", 200)) break;
		timeout--; 
		delay_ms(100);
	}
	if(timeout <= 0)
	{
//		printf("mode set failed\n");
		return;
	}
//	printf("singel conn mod ok\n");
	delay_ms(500);
	
	//set transfer mode 
	ESP8266_Clear();
	timeout = 50;
	while(timeout > 0)
	{
		if(!ESP8266_SendCmd("AT+CIPMODE=1\r\n", "OK", 200)) break;
		timeout--; 
		delay_ms(100);
	}
	if(timeout <= 0)
	{
//		printf("mode set failed\n");
		return;
	}
//	printf("transfermode mod ok\n");
	delay_ms(500);
	
	//connect server
	ESP8266_Clear();
	memset(buff,0,sizeof(buff));
	sprintf(buff,"AT+CIPSTART=\"%s\",\"%s\",%d\r\n","TCP",SERVER_ADDR,SERVER_PORT);
	while(ESP8266_SendCmd(buff, "OK", 500))
	{
		delay_ms(200);
	}
//	printf("server connected\n");
	delay_ms(500);
	
	//start send
	ESP8266_Clear();
	timeout = 1000;
	while(timeout > 0)
	{
		if(!ESP8266_SendCmd("AT+CIPSEND\r\n", "OK", 200)) break;
		timeout--; 
		delay_ms(200);
	}
	if(timeout <= 0)
	{
//		printf("send mode set failed\n");
		return;
	}
	delay_ms(50);
//	printf("start to send!\n");
}
