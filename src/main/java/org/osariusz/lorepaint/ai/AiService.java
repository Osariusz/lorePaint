package org.osariusz.lorepaint.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AiService {

    @Value("${AI_URL}")
    private String AI_URL;

    @Value("${AI_MODEL}")
    private String AI_MODEL;

    public String generatePlaceDescription(AiPlaceDescriptionDTO data) {
        RestClient restClient = RestClient.builder()
                .baseUrl(AI_URL)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        String base = "### TASK ###\nYou are a writer who's job is to write a beautiful description of a place. You will be presented with some information about the place and your job is to use your imagination and develop a completely new, interesting story for this place. Output only the description and nothing else. Your answer should be short but creative. Place info:\n";

        String prompt = base+"name: "+data.name+" details: "+data.info+ "\n### AI RESPONSE ###";

        ObjectNode generationObject = mapper.createObjectNode();
        generationObject.put("model", AI_MODEL);
        generationObject.put("prompt", prompt);
        generationObject.put("stream", false);

        try {
            String generationString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generationObject);
            AIAnswerDTO result = restClient.post()
                    .uri("/api/generate")
                    .body(generationString)
                    .retrieve()
                    .body(AIAnswerDTO.class);
            assert result != null;
            return result.getResponse();

        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
