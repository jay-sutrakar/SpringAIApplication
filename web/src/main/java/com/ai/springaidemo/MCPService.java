package com.ai.springaidemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MCPService {
    private static final Logger log = LoggerFactory.getLogger(MCPService.class.getName());
    private ChatClient chatClient;

    public MCPService(OllamaChatModel ollamaChatModel, ToolCallbackProvider toolCallbackProvider) {
        Arrays.stream(toolCallbackProvider.getToolCallbacks())
                .forEach(toolCallback -> {
                    log.info("Tool call back found {}", toolCallback.getToolDefinition());
                });
        chatClient = ChatClient.builder(ollamaChatModel)
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }


    public String getResponse() {
        return chatClient.prompt("What are Dan Vega's latest youtube videos")
                .call()
                .content();
    }
}
