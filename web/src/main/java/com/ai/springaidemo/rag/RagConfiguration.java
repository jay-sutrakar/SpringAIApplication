package com.ai.springaidemo.rag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class RagConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final String vectorStoreName = "vectorStore.json";
    @Value("classpath:/data/models.json")
    private Resource models;

    @Bean
    SimpleVectorStore simpleVectorStore(OllamaEmbeddingModel embeddingModel) {
        var simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        var vectorStoreFile = getVectorFileStore();
        if (vectorStoreFile.exists()) {
            logger.info("Vector store already exists at {}", vectorStoreFile.getAbsolutePath());
            simpleVectorStore.load(vectorStoreFile);
        } else {
            logger.info("Creating vector store file at {}", vectorStoreFile.getAbsolutePath());
            TextReader textReader = new TextReader(models);
            textReader.getCustomMetadata().put("filename", "models.txt");
            List<Document> documents = textReader.get();
            var tokenTextSplitter = new TokenTextSplitter();
            List<Document> splitDocument = tokenTextSplitter.apply(documents);
            simpleVectorStore.add(splitDocument);
            simpleVectorStore.save(vectorStoreFile);
        }
        return simpleVectorStore;
    }

    private File getVectorFileStore() {
        // Read from environment variable or use default
        String dataDir = System.getenv().getOrDefault(
                "VECTOR_STORE_PATH",
                "web/src/main/resources/data"  // Default for local
        );

        Path path = Paths.get(dataDir, vectorStoreName);

        // Create directory if doesn't exist
        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Cannot create vector store directory: " + path.getParent(), e);
        }

        return path.toFile();
    }

}
