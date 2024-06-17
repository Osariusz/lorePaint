package org.osariusz.lorepaint;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.osariusz.lorepaint.ai.AiService;

public class AITests {

    @InjectMocks
    private AiService aiService = new AiService();

    @Test
    public void placeGenerate() {
        //TODO: mock the service for generation
        //aiService.generatePlaceDescription("siema");
    }
}
