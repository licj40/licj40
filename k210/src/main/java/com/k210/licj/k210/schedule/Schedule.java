package com.k210.licj.k210.schedule;

import com.k210.licj.k210.service.CtrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableAsync
public class Schedule {

    @Autowired
    private CtrlService ctrlService;


    @Scheduled(fixedRate = 10000L)
    @Async
    public void doTask() throws Exception {
        log.info("定时任务执行");
        ctrlService.ctrl();
    }

}
