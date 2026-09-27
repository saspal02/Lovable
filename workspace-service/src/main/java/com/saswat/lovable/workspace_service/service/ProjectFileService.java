package com.saswat.lovable.workspace_service.service;


import com.saswat.lovable.common_lib.dto.FileTreeDto;
import com.saswat.lovable.workspace_service.dto.project.FileContentResponse;

public interface ProjectFileService {
    FileTreeDto getFileTree(Long projectId);

    String getFileContent(Long projectId, String path);

    void saveFile(Long projectId, String filePath, String fileContent);
}
