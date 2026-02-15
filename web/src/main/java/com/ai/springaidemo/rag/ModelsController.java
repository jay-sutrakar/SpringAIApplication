package com.ai.springaidemo.rag;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.constant.ModuleDesc;

@RestController
public class ModelsController {
    private final ChatClient chatClient;

    public ModelsController(OllamaChatModel ollamaChatModel, VectorStore vectorStore) {
        this.chatClient = ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
    }

    @GetMapping("/rag/models")
    public Models faq(@RequestParam(value = "message", defaultValue = "Give me the list of all the models from openAI along with their context window.") String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Models.class);
    }
}
