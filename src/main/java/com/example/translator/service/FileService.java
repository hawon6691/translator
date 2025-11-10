package com.example.translator.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dao.FileDao;
import com.example.translator.dto.File;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileDao fileDao;

    @Transactional
    public File addFile(String fileName, String filePath, String fileType) {
        return fileDao.addFile(fileName, filePath, fileType);
    }

    @Transactional(readOnly = true)
    public File getFile(Long fileId) {
        return fileDao.getFile(fileId);
    }

    @Transactional(readOnly = true)
    public List<File> getAllFiles() {
        return fileDao.getAllFiles();
    }

    @Transactional
    public void deleteFile(Long fileId) {
        fileDao.deleteFile(fileId);
    }
}
