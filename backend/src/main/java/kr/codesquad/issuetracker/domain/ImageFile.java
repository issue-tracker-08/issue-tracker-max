package kr.codesquad.issuetracker.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageFile {

    private final String originalFilename;
    private final String contentType;
    private final InputStream imageInputStream;
    private final Long fileSize;

    public ImageFile(MultipartFile multipartFile) {
        this.originalFilename = getImageFileName(multipartFile);
        this.contentType = getImageContentType(multipartFile);
        this.imageInputStream = getImageInputStream(multipartFile);
        this.fileSize = multipartFile.getSize();
    }

    public String getImageFileName(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            throw new ApplicationException(ErrorCode.EMPTY_FILE);
        }
        return multipartFile.getOriginalFilename().toLowerCase();
    }

    public String getRandomName() {
        StringBuilder randomName = new StringBuilder();
        randomName.append(UUID.randomUUID())
                .append("_")
                .append(originalFilename);
        return randomName.toString();
    }

    public String getImageContentType(MultipartFile multipartFile) {
            return ImageContentType.findEnum(multipartFile.getContentType()).getContentType();
    }

    public InputStream getImageInputStream(MultipartFile multipartFile) {
        try {
            return multipartFile.getInputStream();
        } catch (IOException e) {
            throw new ApplicationException(ErrorCode.FILE_IO_EXCEPTION);
        }
    }

    public static ImageFile from(MultipartFile multipartFile) {
        return new ImageFile(multipartFile);
    }

    @Getter
    @RequiredArgsConstructor
    enum ImageContentType {

        JPEG("image/jpeg"),
        JPG("image/jpg"),
        PNG("image/png");

        private final String contentType;

        public static ImageContentType findEnum(String contentType) {
            for (ImageContentType imageContentType : ImageContentType.values()) {
                if (imageContentType.getContentType().equals(contentType)) {
                    return imageContentType;
                }
            }
            throw new ApplicationException(ErrorCode.INVALID_FILE_EXTENSION);
        }
    }
}
