package client;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ibm.cloud.sdk.core.http.HttpConfigOptions;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.cloud.sdk.core.service.exception.NotFoundException;
import com.ibm.cloud.sdk.core.service.exception.RequestTooLargeException;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.watson.common.WatsonHttpHeaders;
import com.ibm.watson.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.tone_analyzer.v3.model.ToneOptions;

import resources.Tone;

public class WatsonAPI {
    private static final String URL =
        "https://api.kr-seo.tone-analyzer.watson.cloud.ibm.com/instances/4fe7ca2d-cd04-4b55-9a14-3a1eb423513b";
    private static final String API_KEY = "VVbEu7cipOQb3OKcjG7X09AhwDFnq925gNQ4Blz9lScb";
    private final String textToBeToneAnalyzed;

    public WatsonAPI(String textToBeToneAnalyzed) {
        this.textToBeToneAnalyzed = textToBeToneAnalyzed;
    }

    public Tone result() {
        try {
            IamAuthenticator authenticator = new IamAuthenticator(API_KEY);
            ToneAnalyzer toneAnalyzer = new ToneAnalyzer("2017-09-21", authenticator);
            toneAnalyzer.setServiceUrl(URL);

            HttpConfigOptions configOptions = new HttpConfigOptions.Builder().disableSslVerification(true).build();
            toneAnalyzer.configureClient(configOptions);

            ToneOptions toneOptions = new ToneOptions.Builder()
                .text(this.textToBeToneAnalyzed)
                .build();


            Map<String, String> headers = new HashMap<>();
            headers.put(WatsonHttpHeaders.X_WATSON_LEARNING_OPT_OUT, "1");
            headers.put(WatsonHttpHeaders.X_WATSON_TEST, "1");
            toneAnalyzer.setDefaultHeaders(headers);

            ToneAnalysis tone = toneAnalyzer.tone(toneOptions)
                .execute()
                .getResult();


            var listOfTones = tone.getDocumentTone().getTones();
            System.out.println(listOfTones);
            System.out.println("Document tone acquired");
            String toneAcquired = listOfTones.stream().reduce((toneScore, toneScore2) -> {
                if (toneScore.getScore() > toneScore2.getScore()) {
                    return toneScore;
                } else {
                    return toneScore2;
                }
            }).map(a -> a.getToneId()).orElse("NONE");
            System.out.println("Document tone specified");

            var x = Arrays.stream(Tone.values()).filter(s -> s.toString().equalsIgnoreCase(toneAcquired)).findFirst()
                .orElse(Tone.NONE);
            System.out.println("Tone is : " + x);
            return x;

        } catch (NotFoundException e) {
            e.printStackTrace();
            System.out.println("not found");
        } catch (RequestTooLargeException e) {
            System.out.println("request header too large");
        } catch (ServiceResponseException e) {
            System.out.println("Service returned status code "
                + e.getStatusCode() + ": " + e.getMessage());
        }
        return Tone.NONE;
    }
}
