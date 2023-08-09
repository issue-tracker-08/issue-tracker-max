package kr.codesquad.issuetracker.presentation;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.issuetracker.application.S3Service;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final S3Service s3Service;

    // 이미지 업로드
    @PostMapping("/images/upload")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestPart MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("fileUrl", s3Service.uploadImage(image)));
    }
}
