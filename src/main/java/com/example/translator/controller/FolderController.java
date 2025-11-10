package com.example.translator.controller;

import com.example.translator.dto.Folder;
import com.example.translator.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    @GetMapping
    public String listFolders(@RequestParam(required = false) Long userId, Model model) {
        List<Folder> folders = (userId != null)
                ? folderService.getFoldersByUser(userId)
                : folderService.getAllFolders();
        model.addAttribute("folders", folders);
        return "folder-list";
    }

    @GetMapping("/create")
    public String createFolderForm(Model model) {
        model.addAttribute("folder", new Folder());
        return "folder-create";
    }

    @PostMapping("/create")
    public String createFolder(@ModelAttribute Folder folder, RedirectAttributes redirectAttributes) {
        try {
            folderService.addFolder(folder.getUser_id(), folder.getFolder_name());
            redirectAttributes.addFlashAttribute("successMessage", "폴더가 생성되었습니다.");
            return "redirect:/folders";
        } catch (Exception e) {
            log.error("Failed to create folder", e);
            redirectAttributes.addFlashAttribute("errorMessage", "폴더 생성에 실패했습니다.");
            return "redirect:/folders/create";
        }
    }

    // 폴더 삭제 API
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteFolder(@PathVariable("id") Long folderId) {
        try {
            folderService.deleteFolder(folderId);
            return ResponseEntity.ok().body("{\"message\": \"폴더가 삭제되었습니다.\"}");
        } catch (Exception e) {
            log.error("Failed to delete folder: {}", folderId, e);
            return ResponseEntity.internalServerError()
                    .body("{\"message\": \"폴더 삭제에 실패했습니다.\"}");
        }
    }
}