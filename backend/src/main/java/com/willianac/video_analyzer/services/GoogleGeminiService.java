package com.willianac.video_analyzer.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.protobuf.ByteString;
import com.willianac.video_analyzer.exceptions.SummaryErrorsEnum;
import com.willianac.video_analyzer.exceptions.SummaryException;

import jakarta.annotation.PostConstruct;

@Service
public class GoogleGeminiService {
    @Value("${google.gemini.apikey}")
    private String apiKey;

    private Client geminiClient;

    public String transcribeAudio(byte[] audioData) throws Exception {
        try (SpeechClient speechClient = SpeechClient.create()) {
            ByteString audioBytes = ByteString.copyFrom(audioData);
            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();

            RecognitionConfig config = RecognitionConfig.newBuilder()
                    .setSampleRateHertz(16000) // Adjust sample rate as needed
                    .setLanguageCode("pt-BR") // Set the language
                    .build();
            RecognizeResponse response = speechClient.recognize(config, audio);
            SpeechRecognitionResult result = response.getResultsList().get(0);
            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
            System.out.printf("Transcription: %s%n", alternative.getTranscript());

            return alternative.getTranscript();
        } catch (Exception e) {
            System.out.println("GOT ERROR: " + e.getMessage());
            throw new SummaryException(SummaryErrorsEnum.TRANSCRIPTION_FAILED);
        }
    }

    @PostConstruct
    public void init() {
        geminiClient = Client.builder().apiKey(apiKey).build();
    }

    public String summarize(String text, String prompt) throws Exception {
        try {
            GenerateContentResponse response = geminiClient.models.generateContent(
                "gemini-2.5-flash", // Replace with the appropriate model name
                prompt + "\n\n" + text,
                null
            );
            System.out.println("Summary: " + response.text());
            return response.text();
        } catch (Exception e) {
            throw new SummaryException(SummaryErrorsEnum.SUMMARY_FAILED);
        }
    }
}
