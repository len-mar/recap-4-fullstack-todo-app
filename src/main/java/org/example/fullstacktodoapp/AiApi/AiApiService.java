package org.example.fullstacktodoapp.AiApi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;

@Service
public class AiApiService {
    private final RestClient restClient;
// todo: why does maven build fail when triggered manually, but running the full application is completely fine?
    public AiApiService(@Value("${AUTH_KEY}") String key){
            this.restClient = RestClient.builder()
                    .defaultHeader("Authorization", "Bearer " + key)
                    .baseUrl("https://api.openai.com/v1/chat/completions")
                    .build();
        }


    public String spellcheckTodo(String todo) {
        OpenAIRequest request = new OpenAIRequest("gpt-4o-mini",
                List.of(new OpenAiMessage(
                        "user",
                        "if misspelled, replace this with the correct spelling or just give it back unchanged if spelled correctly, never any more words or sentences" + todo)),
                0.2
        );

        // sends request and maps response onto our response object (and returns just the answer field)
        OpenAiResponse response = restClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(OpenAiResponse.class);

        return response.getAnswer();
    }
}
