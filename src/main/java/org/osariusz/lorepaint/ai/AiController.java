package org.osariusz.lorepaint.ai;

import org.osariusz.lorepaint.place.Place;
import org.osariusz.lorepaint.placeUpdate.PlaceUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@PreAuthorize(
        "hasAuthority(@RoleNames.SYSTEM_USER_ROLE_NAME)"
)
public class AiController {

    @Autowired
    private AiService aiService;

    @PostMapping("/generate/description")
    public ResponseEntity<String> updatePlace(@RequestBody String prompt) {
        String result = aiService.generatePlaceDescription(prompt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
