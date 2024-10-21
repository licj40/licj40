#include "lock.h"

void LOCK_Init(void)
{
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOB,ENABLE);
	GPIO_InitTypeDef gpio_config;
	gpio_config.GPIO_Mode = GPIO_Mode_Out_PP;
	gpio_config.GPIO_Pin = GPIO_Pin_6;
	gpio_config.GPIO_Speed = GPIO_Speed_50MHz;
	GPIO_Init(GPIOB,&gpio_config);
	
	PBout(6) = 0;
}

void LOCK_Action(u8 lock_sta)
{
	PBout(6) = lock_sta;
}
