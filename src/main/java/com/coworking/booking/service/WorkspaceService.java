package com.coworking.booking.service;

import com.coworking.booking.model.Workspace;
import com.coworking.booking.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceService {

    private final WorkspaceRepository repository;

    public WorkspaceService(WorkspaceRepository repository) {
        this.repository = repository;
    }

    public List<Workspace> findAll() {
        return repository.findAll();
    }

    public Workspace save(Workspace workspace) {
        return repository.save(workspace);
    }

    public Workspace findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
