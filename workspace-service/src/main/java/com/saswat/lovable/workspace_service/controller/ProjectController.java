package com.saswat.lovable.workspace_service.controller;

import com.saswat.lovable.workspace_service.dto.project.DeployResponse;
import com.saswat.lovable.workspace_service.dto.project.PreviewStatusResponse;
import com.saswat.lovable.workspace_service.dto.project.ProjectRequest;
import com.saswat.lovable.workspace_service.dto.project.ProjectResponse;
import com.saswat.lovable.workspace_service.dto.project.ProjectSummaryResponse;
import com.saswat.lovable.workspace_service.service.DeploymentService;
import com.saswat.lovable.workspace_service.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final DeploymentService deploymentService;

    @GetMapping
    public ResponseEntity<List<ProjectSummaryResponse>> getMyProjects() {
        return ResponseEntity.ok(projectService.getUserProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectSummaryResponse> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getUserProjectById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.softDelete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deploy")
    public ResponseEntity<DeployResponse> deployProject(@PathVariable Long id) {
        return ResponseEntity.ok(deploymentService.deploy(id));
    }

    @GetMapping("/{id}/preview-status")
    @PreAuthorize("@security.canViewProject(#id)")
    public ResponseEntity<PreviewStatusResponse> getPreviewStatus(@PathVariable Long id) {
        return ResponseEntity.ok(deploymentService.getStatus(id));
    }

}

















