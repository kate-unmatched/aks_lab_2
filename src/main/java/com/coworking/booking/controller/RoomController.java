package com.coworking.booking.controller;

import com.coworking.booking.entity.Room;
import com.coworking.booking.entity.Workspace;
import com.coworking.booking.service.RoomService;
import com.coworking.booking.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/workspaces/{workspaceId}/rooms")
public class RoomController {

    private final RoomService roomService;
    private final WorkspaceService workspaceService;

    @GetMapping
    public String list(@PathVariable Long workspaceId, Model model) {
        model.addAttribute("workspace", workspaceService.getById(workspaceId));
        model.addAttribute("rooms", roomService.getRoomsByWorkspace(workspaceId));
        return "rooms/list";
    }


    @GetMapping("/create")
    public String createRoomForm(@PathVariable Long workspaceId, Model model) {
        Workspace workspace = workspaceService.getById(workspaceId);

        Room room = new Room();
        room.setWorkspace(workspace);

        model.addAttribute("room", room);
        model.addAttribute("workspace", workspace);

        return "rooms/create";
    }

    @PostMapping
    public String createRoom(@PathVariable Long workspaceId, @ModelAttribute Room room) {
        Workspace workspace = workspaceService.getById(workspaceId);
        room.setWorkspace(workspace);

        roomService.create(room);
        return "redirect:/workspaces/" + workspaceId + "/rooms";
    }


    @GetMapping("/{roomId}/edit")
    public String editForm(@PathVariable Long workspaceId, @PathVariable Long roomId, Model model) {
        Room room = roomService.getById(roomId);
        Workspace workspace = workspaceService.getById(workspaceId);

        model.addAttribute("room", room);
        model.addAttribute("workspace", workspace);

        return "rooms/edit";
    }

    @PostMapping("/{roomId}/edit")
    public String edit(@PathVariable Long workspaceId, @PathVariable Long roomId, @ModelAttribute Room room) {
        roomService.updateRoom(workspaceId, roomId, room);
        return "redirect:/workspaces/" + workspaceId + "/rooms";
    }

    @PostMapping("/{roomId}/delete")
    public String delete(@PathVariable Long workspaceId, @PathVariable Long roomId) {
        roomService.deleteRoom(workspaceId, roomId);
        return "redirect:/workspaces/" + workspaceId + "/rooms";
    }
}
