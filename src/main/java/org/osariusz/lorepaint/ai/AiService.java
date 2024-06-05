package org.osariusz.lorepaint.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.*;
import org.modelmapper.ModelMapper;
import org.osariusz.lorepaint.loreRole.LoreRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRole;
import org.osariusz.lorepaint.loreUserRole.LoreUserRoleService;
import org.osariusz.lorepaint.map.MapService;
import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.user.User;
import org.osariusz.lorepaint.user.UserService;
import org.osariusz.lorepaint.utils.RoleNames;
import org.osariusz.lorepaint.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AiService {

    @Value("${AI_URL}")
    private String AI_URL;

    @Value("${AI_MODEL}")
    private String AI_MODEL;

    public String generatePlaceDescription(String prompt) {
        RestClient restClient = RestClient.builder()
                .baseUrl(AI_URL)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        ObjectNode generationObject = mapper.createObjectNode();
        generationObject.put("model", AI_MODEL);
        generationObject.put("prompt", prompt);
        generationObject.put("stream", false);

        try {
            String generationString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(generationObject);
            String result = restClient.post()
                    .uri("/api/generate")
                    .body(generationString)
                    .retrieve()
                    .body(String.class);
            return result;

        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
