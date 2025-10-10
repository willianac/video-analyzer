package com.willianac.video_analyzer.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.willianac.video_analyzer.services.GoogleGeminiService;
import com.willianac.video_analyzer.services.VideoDownloaderService;
import com.willianac.video_analyzer.services.YoutubeVideoSummarizer;

@RestController
@RequestMapping("/video-info")
public class VideoInfoController {
    @Autowired
    private VideoDownloaderService videoDownloaderService;

    @Autowired
    private YoutubeVideoSummarizer youtubeVideoSummarizer;

    @Autowired
    public GoogleGeminiService googleGeminiService;

    @GetMapping
    public ResponseEntity<?> getVideoInfo(@RequestParam String videoId) {
        VideoInfo result = videoDownloaderService.getVideoInfo(videoId);
        VideoDetails details = result.details();
        System.out.println("Title: " + details.title());
        System.out.println("Author: " + details.author());
        System.out.println("Length: " + details.lengthSeconds() + " seconds");

        return ResponseEntity.ok(details.title());
    }

    @GetMapping("/download")
    public ResponseEntity<?> downloadVideo(@RequestParam String videoId) {
        try {
            youtubeVideoSummarizer.summarizeVideo(videoId);
            return ResponseEntity.ok("Video download and processing completed.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/transcription")
    public ResponseEntity<?> transcription() {
        try {
            Path file = Paths.get("").toAbsolutePath().resolve("backend/my_videos/25b2d357-f4eb-455c-b81e-1e1a33a59b7a.mp3");
            byte[] audioData = Files.readAllBytes(file);
            String result = googleGeminiService.transcribeAudio(audioData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error in audio transcription");
        }
    }
}
