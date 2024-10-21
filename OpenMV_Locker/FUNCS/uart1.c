#include "uart1.h"
#include "stdio.h"


char Usart1RecBuf[USART1_RXBUFF_SIZE];//����1�������ݻ���
unsigned int RxCounter = 0;   //����1�յ����ݱ�־λ

void UART1_Init(void)
{
	  //GPIO�˿�����
  GPIO_InitTypeDef GPIO_InitStructure;
  USART_InitTypeDef USART_InitStructure;
  NVIC_InitTypeDef NVIC_InitStructure;	 
  RCC_APB2PeriphClockCmd(RCC_APB2Periph_USART1|RCC_APB2Periph_GPIOA|RCC_APB2Periph_AFIO, ENABLE);
	
	USART_DeInit(USART1);  
 
     //USART1_TX   PA.9
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_9;
  GPIO_InitStructure.GPIO_Speed = GPIO_Speed_50MHz;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_AF_PP;
  GPIO_Init(GPIOA, &GPIO_InitStructure);
   
    //USART1_RX	  PA.10
  GPIO_InitStructure.GPIO_Pin = GPIO_Pin_10;
  GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IN_FLOATING;
  GPIO_Init(GPIOA, &GPIO_InitStructure);  

   //Usart1 NVIC ����

  NVIC_InitStructure.NVIC_IRQChannel = USART1_IRQn;
  NVIC_InitStructure.NVIC_IRQChannelPreemptionPriority=0 ;
  NVIC_InitStructure.NVIC_IRQChannelSubPriority = 0;		

  NVIC_InitStructure.NVIC_IRQChannelCmd = ENABLE;	//IRQͨ��ʹ��
  NVIC_Init(&NVIC_InitStructure);	//����NVIC_InitStruct��ָ���Ĳ�����ʼ������NVIC�Ĵ���USART1
  
   //USART ��ʼ������
  USART_InitStructure.USART_BaudRate = 115200;//һ������Ϊ9600;
  USART_InitStructure.USART_WordLength = USART_WordLength_8b;
  USART_InitStructure.USART_StopBits = USART_StopBits_1;
  USART_InitStructure.USART_Parity = USART_Parity_No;
  USART_InitStructure.USART_HardwareFlowControl = USART_HardwareFlowControl_None;
  USART_InitStructure.USART_Mode = USART_Mode_Rx | USART_Mode_Tx;

  USART_Init(USART1, &USART_InitStructure);
   

  USART_Init(USART1, &USART_InitStructure);     //��ʼ������1
  USART_ITConfig(USART1, USART_IT_RXNE, ENABLE);//��������1�����ж�
  USART_ITConfig(USART1, USART_IT_IDLE, DISABLE);//ʹ�ܴ���1���߿����ж�/
  USART_Cmd(USART1, ENABLE);                    
}

void USART1_IRQHandler(void)                	//����1�жϷ������
{
		if(USART_GetITStatus(USART1, USART_IT_RXNE) != RESET)  //�����жϣ�������չ������
		{
			  if(RxCounter >= USART1_RXBUFF_SIZE) RxCounter = 0;
				Usart1RecBuf[RxCounter++] =USART_ReceiveData(USART1);//����ģ�������
    } 
		 USART_ClearITPendingBit(USART1, USART_IT_RXNE);
} 

void uart1_SendStr(char*SendBuf)//����1��ӡ����
{
	while(*SendBuf)
	{
	  while((USART1->SR&0X40)==0);//�ȴ�������� 
    USART1->DR = (u8) *SendBuf; 
		SendBuf++;
	}
}

void uart1_send(unsigned char *bufs,unsigned char len)
{
	  if(len != 0xFF)
		{
				while (len--)
				{
						while((USART1->SR&0X40)==0);//�ȴ�������� 
						USART1->DR = (u8) *bufs; 
						bufs ++;
				}
		}
		else//����ֳ����ڻ򳬹�255�������û�д���ֳ�����
    {
        for (; *bufs != 0;	bufs++) 	//���ַ�������ͳ�ȥ
			  {
						while((USART1->SR&0X40)==0);//�ȴ�������� 
						USART1->DR = (u8) *bufs; 
				}
    }
}

u8 uart1_TX_wifimsg(char* tx_msg)
{
	u16 ch_counter = 0;
	while(*tx_msg)
	{
	  while((USART1->SR&0X40)==0);//�ȴ�������� 
    USART1->DR = (u8) *tx_msg; 
		tx_msg++;
		ch_counter++;
		if(ch_counter > 1020)break;
	}
//	printf("\tnum of send %d\n",ch_counter);
	
	return 0;
}
