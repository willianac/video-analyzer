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

    public String summarizeVideo(String videoId) throws Exception {
        try {
            UUID uuid = UUID.randomUUID();
            videoDownloaderService.downloadVideo(videoId, uuid.toString());

            Path inputPath = Paths.get("").toAbsolutePath().resolve("backend/my_videos/" + uuid.toString() + ".mp4");
            Path outputPath = Paths.get("").toAbsolutePath().resolve("backend/my_videos/" + uuid.toString() + ".mp3");

            audioExtractorService.extractAudio(inputPath.toString(), outputPath.toString());

            byte[] audioData = Files.readAllBytes(outputPath);
            String textFromAudio = googleGeminiService.transcribeAudio(audioData);
            String summary = googleGeminiService.summarize(
                textFromAudio, 
                "Gere um resumo de no máximo 3 linhas, em texto corrido, sobre o texto a seguir retirado de um vídeo:"
            );
            return summary;
        } catch (Exception e) {
            System.out.println("Error in summarizeVideo: " + e.getMessage());
            throw e;
        }
    }
}
