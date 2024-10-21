#include "systick.h"

static u32 fac_ns;
static u32 fac_ms;

void SYSTICK_Init(void)
{
	SysTick_CLKSourceConfig(SysTick_CLKSource_HCLK_Div8);//外部时钟8分频
	fac_ns = 9;//8000000 / 8 = 1000000 也就是1000000/1s 正好是 1ns
	fac_ms =fac_ns * 1000;//1s = 1000ms = 1000000ns
}

void delay_ns(u32 ns)
{
	SysTick->LOAD = ns * fac_ns;//重装载值
	SysTick->VAL = 0;//当前值
	SysTick->CTRL |= (u32)(0x01 << 0);//第32:0位置1启动systick计时器
	u32 temp = 0;
	do
	{
		temp = SysTick->CTRL;
	}
	while((temp & 0x01 << 0) && !(temp & 0x01 << 16));//计时器开启状态并且记数未完成时运行
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
