package org.osariusz.lorepaint.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequestMapping("/api")
public class GreetingController {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/broadcast")
    @SendTo("/topic/reply")
    public String greeting(@Payload String message) throws Exception {
        messagingTemplate.convertAndSend("/api/topic/reply", (message));
        return message;
    }

}