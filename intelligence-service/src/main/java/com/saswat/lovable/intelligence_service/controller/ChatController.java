package com.saswat.lovable.intelligence_service.controller;

import com.saswat.lovable.intelligence_service.dto.chat.ChatRequest;
import com.saswat.lovable.intelligence_service.dto.chat.ChatResponse;
import com.saswat.lovable.intelligence_service.dto.chat.StreamResponse;
import com.saswat.lovable.intelligence_service.service.AiGenerationService;
import com.saswat.lovable.intelligence_service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    private final AiGenerationService aiGenerationService;
    private final ChatService chatService;

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<StreamResponse>> streamChat(
            @RequestBody ChatRequest request,
            @RequestHeader("Authorization") String authorization) {

        return aiGenerationService.streamResponse(request.message(), request.projectId(), authorization)
                .map(data -> ServerSentEvent.<StreamResponse>builder()
                        .data(data)
                        .build());
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<ChatResponse>> getChatHistory(
            @PathVariable Long projectId) {

        return ResponseEntity.ok(chatService.getProjectChatHistory(projectId));
    }
}
