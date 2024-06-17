package org.osariusz.lorepaint.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize(
        "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)"
)
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping("/generate/description")
    public ResponseEntity<String> updatePlace(@RequestBody AiPlaceDescriptionDTO prompt) {
        String result = aiService.generatePlaceDescription(prompt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
