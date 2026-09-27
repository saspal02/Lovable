package com.saswat.lovable.workspace_service.mapper;

import com.saswat.lovable.common_lib.dto.FileNode;
import com.saswat.lovable.workspace_service.entity.ProjectFile;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectFileMapper {

    List<FileNode> toListOfFileNode(List<ProjectFile> projectFileList);
}
