package com.willianac.video_analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VideoAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoAnalyzerApplication.class, args);
	}

}
