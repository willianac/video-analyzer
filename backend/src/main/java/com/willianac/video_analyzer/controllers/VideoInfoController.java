package com.willianac.video_analyzer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.willianac.video_analyzer.services.VideoDownloaderService;

@RestController
@RequestMapping("/video-info")
public class VideoInfoController {
    @Autowired
    private VideoDownloaderService videoDownloaderService;

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
        videoDownloaderService.downloadVideo(videoId);
        return ResponseEntity.ok("Video download initiated.");
    }
}
