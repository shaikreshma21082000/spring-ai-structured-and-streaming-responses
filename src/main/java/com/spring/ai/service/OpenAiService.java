package com.spring.ai.service;


import com.spring.ai.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final ChatClient chatClient;

    private final ChatClient structuredChatClient;

    public String getResponse(String question) {

        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }

    public Flux<String> getStreamResponse(String question) {
        return chatClient.prompt()
                .user(question)
                .stream().content();
    }

    public Response getEntityResponse(String question) {
        return structuredChatClient.prompt()
                .user(question)
                .call().entity(Response.class);
    }

    public List<String> getCitiesResponse(String question) {
        return structuredChatClient.prompt()
                .user(question)
                .call()
                .entity(new ListOutputConverter());
    }

    public Map<String,Object> getResponseMap(String question) {
        return structuredChatClient.prompt()
                .user(question)
                .call()
                .entity(new MapOutputConverter());
    }

    public Response getResponseBean(String question) {
        return structuredChatClient.prompt()
                .user(question)
                .call()
                .entity(new BeanOutputConverter<Response>(Response.class));
    }

    public List<Response> getResponseParamBean(String question) {
        return structuredChatClient.prompt()
                .user(question)
                .call()
                .entity(new ParameterizedTypeReference<List<Response>>(){});
    }
}
