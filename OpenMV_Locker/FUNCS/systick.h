#ifndef _SYSTICK_H_
#define _SYSTICK_H_
#include "sys.h"

void SYSTICK_Init(void);
void delay_ns(u32 ns);
void delay_ms(u32 ms);


#endif
