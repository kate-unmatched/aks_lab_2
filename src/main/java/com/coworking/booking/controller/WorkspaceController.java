package com.coworking.booking.controller;

import com.coworking.booking.model.Workspace;
import com.coworking.booking.service.WorkspaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService service;

    public WorkspaceController(WorkspaceService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("workspaces", service.findAll());
        return "workspace-list";
    }

    @PostMapping
    public String create(@ModelAttribute Workspace workspace) {
        service.save(workspace);
        return "redirect:/workspaces";
    }
}
