package com.willianac.video_analyzer.services;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Service;

import com.willianac.video_analyzer.exceptions.SummaryErrorsEnum;
import com.willianac.video_analyzer.exceptions.SummaryException;

@Service
public class AudioExtractorService {
    public void extractAudio(String videoPath, String audioPath) throws Exception {
        try (FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(videoPath)) {
            frameGrabber.setFormat("mp4"); // Optional; can be removed if auto-detection works
            frameGrabber.start();

            int audioChannels = frameGrabber.getAudioChannels();
            if (audioChannels <= 0) {
                throw new RuntimeException("No audio channels found in input file!");
            }

            try (FFmpegFrameRecorder frameRecorder = new FFmpegFrameRecorder(audioPath, audioChannels)) {
                frameRecorder.setSampleRate(frameGrabber.getSampleRate());
                frameRecorder.setAudioCodec(avcodec.AV_CODEC_ID_MP3); // mp3 format
                frameRecorder.setFormat("mp3");
                frameRecorder.start();

                Frame frame;
                while ((frame = frameGrabber.grabSamples()) != null) {
                    frameRecorder.record(frame);
                }
                
            };
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SummaryException(SummaryErrorsEnum.EXTRACT_AUDIO_FAILED);
        }
    }
}