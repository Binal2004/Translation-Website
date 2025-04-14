
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.*;
import com.google.cloud.translate.v3.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class TranslationCode extends HttpServlet {

    private TextToSpeechClient textToSpeechClient;
    private TranslationServiceClient translationClient;

    @Override
    public void init() {
        try {
            String credentialsPath = "Your Credentials";

            GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath))
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));

            translationClient = TranslationServiceClient.create(TranslationServiceSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build());

            textToSpeechClient = TextToSpeechClient.create(TextToSpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build());
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize Google Cloud clients", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String inputText = request.getParameter("inputText");
        String action = request.getParameter("action");
        String translatedText = null;

        if ("translate".equals(action)) {
            translatedText = translateText(inputText, request.getParameter("targetLanguage"));
            request.setAttribute("translatedText", translatedText);
        } else if ("textToSpeech".equals(action)) {
            byte[] audioData = textToSpeech(inputText);
            request.setAttribute("audioData", audioData);
            request.getRequestDispatcher("/playAudio.jsp").forward(request, response);
            return;
        } else if ("textToSpeechTranslation".equals(action)) {
            translatedText = translateText(inputText, request.getParameter("targetLanguage"));
            byte[] audioData = textToSpeech(translatedText);
            request.setAttribute("translatedText", translatedText);
            request.setAttribute("audioData", audioData);
            request.getRequestDispatcher("/playAudio.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("/result.jsp").forward(request, response);
    }

    private String translateText(String inputText, String targetLanguage) throws IOException {
        TranslateTextRequest request = TranslateTextRequest.newBuilder()
                .setParent("projects/ Your Project ID") // Replace with your project ID
                .addAllContents(Collections.singletonList(inputText))
                .setTargetLanguageCode(targetLanguage)
                .build();

        TranslateTextResponse response = translationClient.translateText(request);
        return response.getTranslations(0).getTranslatedText();
    }

    private byte[] textToSpeech(String text) throws IOException {
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

        VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("en-US")
                .setName("en-US-Wavenet-D")
                .build();

        AudioConfig audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16)
                .build();

        SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
        return response.getAudioContent().toByteArray();
    }

    @Override
    public void destroy() {
        if (textToSpeechClient != null) {
            textToSpeechClient.close();
        }
        if (translationClient != null) {
            translationClient.close();
        }
    }
}
