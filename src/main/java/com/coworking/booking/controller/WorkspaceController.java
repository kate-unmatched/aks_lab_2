package com.coworking.booking.controller;

import com.coworking.booking.entity.Workspace;
import com.coworking.booking.entity.WorkspaceType;
import com.coworking.booking.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("workspaces", workspaceService.getAll());
        return "workspaces/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Long id, Model model) {
        model.addAttribute("workspace", workspaceService.getById(id));
        return "workspaces/details";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("workspace", new Workspace());
        return "workspaces/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Workspace workspace) {
        workspace.setAvailable(true);
        workspaceService.create(workspace);
        return "redirect:/workspaces/list";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        workspaceService.delete(id);
        return "redirect:/workspaces/list";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/workspaces/list";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("workspace", workspaceService.getById(id));
        model.addAttribute("types", WorkspaceType.values());
        return "workspaces/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute Workspace workspace) {
        workspaceService.update(id, workspace);
        return "redirect:/workspaces/list";
    }
}
