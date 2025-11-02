package lk.wedrent.wedrent_backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageStorageService {
    String upload(MultipartFile file, String keyPrefix);
    List<String> uploadMany(List<MultipartFile> files, String keyPrefix);
    void delete(String url);
}
