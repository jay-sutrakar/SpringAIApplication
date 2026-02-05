package com.ai.springaidemo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("ask-ai")
    public String getResponse(@RequestBody String prompt) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(prompt, Map.class);
        return chatService.getResponse(map.get("prompt"));
    }

}
