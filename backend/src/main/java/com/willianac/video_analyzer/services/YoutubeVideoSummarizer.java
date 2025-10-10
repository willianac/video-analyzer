package com.willianac.video_analyzer.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YoutubeVideoSummarizer {
    @Autowired
    private VideoDownloaderService videoDownloaderService;
    @Autowired
    private AudioExtractorService audioExtractorService;
    @Autowired
    private GoogleGeminiService googleGeminiService;

    public String summarizeVideo(String videoId) {
        try {
            UUID uuid = UUID.randomUUID();
            videoDownloaderService.downloadVideo(videoId, uuid.toString());

            Path inputPath = Paths.get("").toAbsolutePath().resolve("backend/my_videos/" + uuid.toString() + ".mp4");
            Path outputPath = Paths.get("").toAbsolutePath().resolve("backend/my_videos/" + uuid.toString() + ".mp3");

            audioExtractorService.extractAudio(inputPath.toString(), outputPath.toString());

            byte[] audioData = Files.readAllBytes(outputPath);
            String summary = googleGeminiService.transcribeAudio(audioData);
            return summary;

        } catch (Exception e) {
            System.out.println("Error in summarizeVideo: " + e.getMessage());
            throw new RuntimeException("Failed to summarize video for video ID: " + videoId, e);
        }
    }
}
