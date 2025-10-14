package com.willianac.video_analyzer.exceptions;

public enum SummaryErrorsEnum {
    VIDEO_TOO_LONG("Video is too long. Maximum allowed duration is 60 seconds."),
    VIDEO_NOT_FOUND("Video not found. Please check the video ID and try again."),
    DOWNLOAD_FAILED("Failed to download video. Please try again later."),
    GET_VIDEO_INFO_FAILED("Failed to fetch video info"),
    EXTRACT_AUDIO_FAILED("Failed to extract audio from video."),
    TRANSCRIPTION_FAILED("Failed to transcribe audio. Please try again later."),
    SUMMARY_FAILED("Failed to generate summary. Please try again later.");

    private final String code;
    private final String message;

    SummaryErrorsEnum(String message) {
        this.code = this.name();
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
