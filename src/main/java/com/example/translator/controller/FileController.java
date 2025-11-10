package com.example.translator.controller;

import com.example.translator.dto.File;
import com.example.translator.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
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
    public String createFile(@ModelAttribute File file, RedirectAttributes redirectAttributes) {
        try {
            fileService.addFile(file.getFile_name(), file.getFile_path(), file.getFile_type());
            redirectAttributes.addFlashAttribute("successMessage", "파일이 업로드되었습니다.");
            return "redirect:/files";
        } catch (Exception e) {
            log.error("Failed to upload file", e);
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드에 실패했습니다.");
            return "redirect:/files/create";
        }
    }

    // 파일 다운로드
    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long fileId) {
        try {
            File file = fileService.getFile(fileId);
            if (file == null) {
                return ResponseEntity.notFound().build();
            }

            // 실제로는 파일 경로에서 파일을 읽어서 반환해야 함
            // 여기서는 간단히 파일 정보만 반환
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + file.getFile_name() + "\"")
                    .body("{\"message\": \"파일 다운로드: " + file.getFile_name() + "\"}");
        } catch (Exception e) {
            log.error("Failed to download file: {}", fileId, e);
            return ResponseEntity.internalServerError()
                    .body("{\"message\": \"파일 다운로드에 실패했습니다.\"}");
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        String uploadDir = "uploads/";
        java.io.File dir = new java.io.File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = uploadDir + multipartFile.getOriginalFilename();
        multipartFile.transferTo(new java.io.File(filePath));

        File saved = fileService.addFile(
                multipartFile.getOriginalFilename(),
                filePath,
                multipartFile.getContentType()
        );
        return ResponseEntity.ok(saved);
    }

    // 파일 삭제
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteFile(@PathVariable("id") Long fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.ok().body("{\"message\": \"파일이 삭제되었습니다.\"}");
        } catch (Exception e) {
            log.error("Failed to delete file: {}", fileId, e);
            return ResponseEntity.internalServerError()
                    .body("{\"message\": \"파일 삭제에 실패했습니다.\"}");
        }
    }
}