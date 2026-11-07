package com.spring.ai.controller;


import com.spring.ai.dto.Response;
import com.spring.ai.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class OpenAiController {

    private final OpenAiService openAiService;

    @PostMapping("/ask")
    public String getData(@RequestParam String question) {
        return openAiService.getResponse(question);
    }

    @GetMapping("/stream-response")
    public Flux<String> getStreamData(@RequestParam String message) {
        return openAiService.getStreamResponse(message);
    }

    @PostMapping("/entity-response")
    public Response getEntityData(@RequestParam String question) {
        return openAiService.getEntityResponse(question);
    }

    @PostMapping("/list-cities-response")
    public List<String> getCitiesData(@RequestParam String question) {
        return openAiService.getCitiesResponse(question);
    }

    @PostMapping("/map-cities-response")
    public Map<String,Object> getCitiesMapData(@RequestParam String question) {
        return openAiService.getResponseMap(question);
    }

    @PostMapping("/bean-cities-response")
    public Response getCitiesBeanData(@RequestParam String question) {
        return openAiService.getResponseBean(question);
    }


    @PostMapping("/parameterised-cities-response")
    public List<Response> getCitiesParameterizedData(@RequestParam String question) {
        return openAiService.getResponseParamBean(question);
    }

}
