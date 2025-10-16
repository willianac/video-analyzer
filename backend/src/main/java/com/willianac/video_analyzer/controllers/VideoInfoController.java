package com.willianac.video_analyzer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.willianac.video_analyzer.model.SummaryRequest;
import com.willianac.video_analyzer.model.User;
import com.willianac.video_analyzer.repository.SummaryRequestRepository;
import com.willianac.video_analyzer.repository.UserRepository;
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

    @Autowired
    public SummaryRequestRepository summaryRequestRepository;

    @Autowired
    public UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getVideoInfo(@RequestParam String videoId) {
        VideoInfo result = videoDownloaderService.getVideoInfo(videoId);
        VideoDetails details = result.details();
        System.out.println("Title: " + details.title());
        System.out.println("Author: " + details.author());
        System.out.println("Length: " + details.lengthSeconds() + " seconds");

        return ResponseEntity.ok(details.title());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/summary")
    public ResponseEntity<?> downloadVideo(@RequestParam String videoId, @RequestParam Long userId) {
        if(videoId == null || videoId.isBlank() || userId == null) {
            return ResponseEntity.badRequest().body("videoId and userId are required");
        }

        try {
            String result = youtubeVideoSummarizer.summarizeVideo(videoId);
            User user = userRepository.findById(userId).orElse(null);
            SummaryRequest summaryRequest = new SummaryRequest();
            summaryRequest.setVideoId(videoId);
            summaryRequest.setSummary(result); 
            summaryRequest.setUser(user);
            summaryRequestRepository.save(summaryRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
