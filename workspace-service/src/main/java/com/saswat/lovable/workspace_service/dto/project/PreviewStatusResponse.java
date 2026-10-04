package com.saswat.lovable.workspace_service.dto.project;

import com.saswat.lovable.common_lib.enums.PreviewStatus;

public record PreviewStatusResponse(
        PreviewStatus status,
        String previewUrl
) {
}
