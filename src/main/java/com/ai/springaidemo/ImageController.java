package com.ai.springaidemo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Base64;


@RestController
public class ImageController {
    @Autowired
    public ImageService imageService;

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

}
