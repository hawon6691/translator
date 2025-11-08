package com.example.translator.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.translator.dao.FolderDao;
import com.example.translator.dto.Folder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderDao folderDao;

    @Transactional
    public Folder addFolder(Long userId, String folderName) {
        return folderDao.addFolder(userId, folderName);
    }

    @Transactional(readOnly = true)
    public Folder getFolder(Long folderId) {
        return folderDao.getFolder(folderId);
    }

    @Transactional(readOnly = true)
    public List<Folder> getFoldersByUser(Long userId) {
        return folderDao.getFoldersByUser(userId);
    }

    @Transactional(readOnly = true)
    public List<Folder> getAllFolders() {
        return folderDao.getAllFolders();
    }
}
