#ifndef _LOCK_H_
#define _LOCK_H_
#include "systick.h"

#define UNLOCK 1
#define LOCK   0

void LOCK_Init(void);
void LOCK_Action(u8 lock_sta);

#endif

