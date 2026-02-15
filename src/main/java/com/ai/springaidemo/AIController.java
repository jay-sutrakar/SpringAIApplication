package com.ai.springaidemo;

import com.google.gson.Gson;
import org.springframework.ai.image.ImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(
        origins = "http://localhost:3000",
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
public class AIController {

    private final ChatService chatService;
    private final ImageService imageService;
    private final MCPService mcpService;

    public AIController(ChatService chatService, ImageService imageService, MCPService mcpService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.mcpService = mcpService;
    }

    @PostMapping("ask-ai")
    public String getResponse(@RequestBody String prompt) {
        Gson gson = new Gson();
        Map<String, String> map = gson.fromJson(prompt, Map.class);
        return chatService.getResponse(map.get("prompt"));
    }

    @GetMapping(path = "generate-image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateImage(@RequestParam String prompt) throws IOException {
        ImageResponse imageResponse = imageService.generateImage(prompt);
        // if string contains data URL prefix, strip it
        String base64 = imageResponse.getResult().getOutput().getB64Json();
        byte[] imageBytes = Base64.getDecoder().decode(base64);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(imageBytes.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageBytes);
    }

    @GetMapping(path = "/mcp/chat")
    public String chat() {
        return mcpService.getResponse();
    }
}
