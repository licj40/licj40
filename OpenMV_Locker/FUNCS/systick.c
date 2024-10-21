#include "systick.h"

static u32 fac_ns;
static u32 fac_ms;

void SYSTICK_Init(void)
{
	SysTick_CLKSourceConfig(SysTick_CLKSource_HCLK_Div8);//�ⲿʱ��8��Ƶ
	fac_ns = 9;//8000000 / 8 = 1000000 Ҳ����1000000/1s ������ 1ns
	fac_ms =fac_ns * 1000;//1s = 1000ms = 1000000ns
}

void delay_ns(u32 ns)
{
	SysTick->LOAD = ns * fac_ns;//��װ��ֵ
	SysTick->VAL = 0;//��ǰֵ
	SysTick->CTRL |= (u32)(0x01 << 0);//��32:0λ��1����systick��ʱ��
	u32 temp = 0;
	do
	{
		temp = SysTick->CTRL;
	}
	while((temp & 0x01 << 0) && !(temp & 0x01 << 16));//��ʱ������״̬���Ҽ���δ���ʱ����
	SysTick->VAL = 0;
	SysTick->CTRL &= ~(0x01 << 0);
}

void delay_ms(u32 ms)
{
	SysTick->LOAD = ms * fac_ms;
	SysTick->VAL = 0;
	SysTick->CTRL |= (u32)(0x01 << 0);
	u32 temp = 0;
	do
	{
		temp = SysTick->CTRL;
	}
	while((temp & 0x01 << 0) && !(temp & 0x01 << 16));
	SysTick->VAL = 0;
	SysTick->CTRL &= ~(0x01 << 0);
}
