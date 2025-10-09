package com.willianac.video_analyzer.services;

import java.io.File;

import org.springframework.stereotype.Service;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.client.ClientType;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

@Service
public class VideoDownloaderService {
    YoutubeDownloader downloader = new YoutubeDownloader();

    public VideoInfo getVideoInfo(String videoId) {
        try {
            RequestVideoInfo request = new RequestVideoInfo(videoId).clientType(ClientType.WEB_PARENT_TOOLS);

            Response<VideoInfo> response = downloader.getVideoInfo(request);

            if (!response.ok() || response.data() == null) {
                System.out.println("Here is the error:");
                System.out.println(response.error());
                throw new RuntimeException("Failed to fetch video info. Response not OK.");
            }

            return response.data();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get video info for video ID: " + videoId, e);
        }
    }

    public void downloadVideo(String videoId, String nameAfterDownload) {
        try {
            VideoInfo video = getVideoInfo(videoId);
            File outputDir = new File("backend/my_videos");
            Format format = video.videoFormats().get(0);

            RequestVideoFileDownload request = new RequestVideoFileDownload(format)
                .saveTo(outputDir)
                .renameTo(nameAfterDownload);
            
            downloader.downloadVideoFile(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to download video for video ID: " + videoId, e);
        }
    }
}
