package com.saswat.lovable.workspace_service.service;

import com.saswat.lovable.workspace_service.dto.project.DeployResponse;
import com.saswat.lovable.workspace_service.dto.project.PreviewStatusResponse;
import org.jspecify.annotations.Nullable;

public interface DeploymentService {
    @Nullable DeployResponse deploy(Long projectId);

    PreviewStatusResponse getStatus(Long projectId);
}
