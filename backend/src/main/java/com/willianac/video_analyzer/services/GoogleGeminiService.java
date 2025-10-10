package com.willianac.video_analyzer.services;

import org.springframework.stereotype.Service;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

@Service
public class GoogleGeminiService {

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
            throw new Exception("Error during transcription: " + e.getMessage());
        }
    }
}
