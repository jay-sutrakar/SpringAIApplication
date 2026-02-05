package com.ai.springaidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class ChatService {
    private final ChatClient chatClient;

    public ChatService(OpenAiChatModel openAiChatModel) {
        this.chatClient = ChatClient.builder(openAiChatModel)
                .build();
    }

    public String getResponse(String prompt) {
        return chatClient.prompt()
                .advisors(a -> {
                    a.param(ChatMemory.CONVERSATION_ID, "1a02");
                })
                .user(prompt)
                .call()
                .content();
    }

}
