package com.ai.springaidemo;

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.stabilityai.StabilityAiImageModel;
import org.springframework.ai.stabilityai.api.StabilityAiImageOptions;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    private final StabilityAiImageModel stabilityAiImageModel;

    public ImageService(StabilityAiImageModel stabilityAiImageModel) {
        this.stabilityAiImageModel = stabilityAiImageModel;
    }

    public ImageResponse generateImage(String prompt) {
        return stabilityAiImageModel.call(new ImagePrompt(prompt, StabilityAiImageOptions.builder()
                .width(640)
                .height(1536)
                .build()));
    }
}
