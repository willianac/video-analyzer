package com.willianac.video_analyzer.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VideosCleanerService {
    
    @Scheduled(fixedDelay = 60000) // Runs every 1 minute
    public void cleanVideosFolder() {
        Path folderPath = Paths.get("backend/my_videos");
        
        try {
            Files.walk(folderPath)
                .sorted(Comparator.reverseOrder())
                .forEach(p -> {
                    try {
                        Files.deleteIfExists(p);
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                });
        } catch (Exception e) {
            System.out.println("Error cleaning videos folder: " + e.getMessage());
        }
    }
}
