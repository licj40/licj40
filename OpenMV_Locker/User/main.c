#include "systick.h"
#include "uart1.h"
#include "uart2.h"
#include "stdio.h"
#include "string.h"
#include "esp8266.h"
#include "lock.h"

char wifi_buff_tx[2048] = {0};

int main(void)
{
	SYSTICK_Init();
	UART1_Init();
	//UART2_Init();
	LOCK_Init();
	//printf("uart2 init completed\n");
	ESP8266_ConnecWifi();
	u32 recv_cnt = 0;
	
	while(1)
	{
		memset(wifi_buff_tx,0,sizeof(wifi_buff_tx));
		sprintf(wifi_buff_tx,"GET /test HTTP/1.1\r\nHost: %s\r\n\r\n",SERVER_ADDR);
		uart1_TX_wifimsg(wifi_buff_tx);
		delay_ms(50);
		for(int i = 0;i < 190;i++)
		{
			if(ESP8266_WaitRecive() == REV_OK)
			{

				if(strstr(Usart1RecBuf,"SUCCESS") != NULL)
				{
					LOCK_Action(UNLOCK);
					//printf("------------------------- -------------------------unlock\n");
				}
				if(strstr(Usart1RecBuf,"ERROR") != NULL)
				{
					LOCK_Action(LOCK);
					//printf("------------------------- -------------------------lock\n");
				}
				ESP8266_Clear();
			}
			delay_ms(5);
		}
		recv_cnt++;
		//delay_ms(1000);
	}
	
	return 0;
}