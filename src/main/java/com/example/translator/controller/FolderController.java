package com.example.translator.controller;

import com.example.translator.dto.Folder;
import com.example.translator.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @GetMapping("/")
    public String redirectToUsers() {
        return "redirect:/users";
    }

    @GetMapping
    public String listFolders(@RequestParam(required = false) Long userId, Model model) {
        List<Folder> folders = (userId != null) ? folderService.getFoldersByUser(userId) : folderService.getAllFolders();
        model.addAttribute("folders", folders);
        return "folder-list";
    }

    @GetMapping("/create")
    public String createFolderForm(Model model) {
        model.addAttribute("folder", new Folder());
        return "folder-create";
    }

    @PostMapping("/create")
    public String createFolder(@ModelAttribute Folder folder) {
        folderService.addFolder(folder.getUser_id(), folder.getFolder_name());
        return "redirect:/folders";
    }
}