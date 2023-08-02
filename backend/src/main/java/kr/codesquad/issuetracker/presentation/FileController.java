package kr.codesquad.issuetracker.presentation;

import kr.codesquad.issuetracker.application.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class FileController {

    private final S3Service s3Service;

    // 이미지 업로드
    public ResponseEntity<Map<String, String>> uploadImage(@RequestPart MultipartFile image) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("fileUrl" ,s3Service.uploadImage(image)));
    @PostMapping("/images/upload")
    }
}
