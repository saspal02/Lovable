package com.saswat.lovable.intelligence_service.llm;

import com.saswat.lovable.common_lib.dto.FileNode;
import com.saswat.lovable.intelligence_service.client.WorkspaceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisor;
import org.springframework.ai.chat.client.advisor.api.StreamAdvisorChain;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileTreeContextAdvisor implements StreamAdvisor {

    private final WorkspaceClient workspaceClient;

    @Override
    public Flux<ChatClientResponse> adviseStream(ChatClientRequest request, StreamAdvisorChain streamAdvisorChain) {
        Map<String, Object> context = request.context();
        Long projectId = Long.parseLong(context.getOrDefault("projectId", 0).toString());
        String authorization = context.getOrDefault("authorization", "").toString();

        ChatClientRequest augmentedChatClientRequest = augmentRequestWithFileTree(request, projectId, authorization);

        return streamAdvisorChain.nextStream(augmentedChatClientRequest);
    }

    private ChatClientRequest augmentRequestWithFileTree(ChatClientRequest request, Long projectId, String authorization) {

        List<Message> incomingMessages = request.prompt().getInstructions();

        Message systemMessage = incomingMessages.stream()
                .filter(m -> m.getMessageType() == MessageType.SYSTEM)
                .findFirst()
                .orElse(null);

        List<Message> userMessages = incomingMessages.stream()
                .filter(m -> m.getMessageType() != MessageType.SYSTEM)
                .toList();

        List<Message> allMessages = new ArrayList<>();

        // Add original system message
        if (systemMessage != null) {
            allMessages.add(systemMessage);
        }

        List<FileNode> fileTree = workspaceClient.getFileTree(projectId, authorization).files();
        String fileTreeContext = "\n\n ---- FILE_TREE ----\n"+fileTree.toString();
        allMessages.add(new SystemMessage(fileTreeContext));

        try {
            String packageJson = workspaceClient.getFileContent(projectId, "package.json", authorization);
            if (packageJson != null && !packageJson.isBlank()) {
                allMessages.add(new SystemMessage("\n\n ---- PACKAGE_JSON ----\n" + packageJson));
            }
        } catch (Exception e) {
            log.warn("Could not load package.json for project {}", projectId);
        }

        allMessages.addAll(userMessages);

        return request
                .mutate()
                .prompt(new Prompt(allMessages, request.prompt().getOptions()))
                .build();
    }


    @Override
    public String getName() {
        return "FileTreeContextAdvisor";
    }

    @Override
    public int getOrder() {
        return 0;
    }
}








