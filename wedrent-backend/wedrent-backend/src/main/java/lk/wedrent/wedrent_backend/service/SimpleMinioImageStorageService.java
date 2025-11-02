package lk.wedrent.wedrent_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Minimal stub implementation for MinIO/S3; replace with real client in Sprint 3.
 */
@Service
public class SimpleMinioImageStorageService implements ImageStorageService {

    @Override
    public String upload(MultipartFile file, String keyPrefix) {
        var key = keyPrefix + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
        return "https://storage.example.com/" + key;
    }

    @Override
    public List<String> uploadMany(List<MultipartFile> files, String keyPrefix) {
        return files.stream().map(f -> upload(f, keyPrefix)).collect(Collectors.toList());
    }

    @Override
    public void delete(String url) {
        // noop for mock
    }
}
