package com.bekzodkeldiyarov.bookshop.service;

import com.bekzodkeldiyarov.bookshop.model.BookFile;
import com.bekzodkeldiyarov.bookshop.repository.BookFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class ResourceStorage {

    @Value("${upload.path}")
    String uploadPath;

    @Value("${download.path}")
    String downloadPath;

    private final BookFileRepository bookFileRepository;

    public ResourceStorage(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }


    public String saveNewImage(MultipartFile file, String slug) throws IOException {
        String resourceUri = null;
        if (!file.isEmpty()) {
            if (!new File(uploadPath).exists()) {
                Files.createDirectories(Paths.get(uploadPath));
                log.info(this.getClass().getSimpleName() + " created directory in " + uploadPath);
            }
            String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(uploadPath, fileName);
            resourceUri = "/book-covers/" + fileName;
            file.transferTo(path);
            log.info(this.getClass().getSimpleName() + " -- " + fileName + " uploaded OK!");
        }
        return resourceUri;
    }

    public Path getBookFilePath(String hash) {
        BookFile bookFile = bookFileRepository.getBookFileByHash(hash);
        return Paths.get(bookFile.getPath());
    }

    public MediaType getBookFileMime(String hash) {
        BookFile bookFile = bookFileRepository.getBookFileByHash(hash);
        String mimeType = URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        if (mimeType != null) {
            return MediaType.parseMediaType(mimeType);
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public byte[] getBookFileByteArray(String hash) throws IOException {
        BookFile bookFile = bookFileRepository.getBookFileByHash(hash);
        Path path = Paths.get(downloadPath, bookFile.getPath());
        log.info(path.toString());
        return Files.readAllBytes(path);
    }
}
