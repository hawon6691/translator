package com.example.translator.controller;

import com.example.translator.dto.File;
import com.example.translator.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping
    public String listFiles(Model model) {
        List<File> files = fileService.getAllFiles();
        model.addAttribute("files", files);
        return "file-list";
    }

    @GetMapping("/create")
    public String createFileForm(Model model) {
        model.addAttribute("file", new File());
        return "file-create";
    }

    @PostMapping("/create")
    public String createFile(@ModelAttribute File file) {
        fileService.addFile(file.getFile_name(), file.getFile_path(), file.getFile_type());
        return "redirect:/files";
    }
}