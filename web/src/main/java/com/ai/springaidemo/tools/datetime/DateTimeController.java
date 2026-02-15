package com.ai.springaidemo.tools.datetime;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DateTimeController {

    private final ChatClient chatClient;
    public DateTimeController(OllamaChatModel ollamaChatModel) {
        chatClient = ChatClient.builder(ollamaChatModel)
                .build();
    }

    @GetMapping("/tools")
    public String tools() {
        return chatClient.prompt()
                .user("What is tomorrow's date in current time zone ?")
                .tools(new DateTimeTools())
                .call()
                .content();
    }
}
