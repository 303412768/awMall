package com.wen.mall.config.listener;

import com.wen.mall.system.order.entity.OrderMailInfo;
import com.wen.mall.tools.MailTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class OderListener {

    @Autowired
    private MailTool mailTool;

    @EventListener
    @Async
    public void orderAddedAndSentMailToVivien(OrderMailInfo mailInfo) {
        mailTool.sendHtmlMail(mailInfo.geteMailTitle(), mailInfo.getMailContent());
    }
}
