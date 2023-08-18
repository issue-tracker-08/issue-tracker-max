package kr.codesquad.issuetracker.application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

import kr.codesquad.issuetracker.application.s3.S3Uploader;
import kr.codesquad.issuetracker.domain.ImageFile;
import kr.codesquad.issuetracker.exception.ApplicationException;
import kr.codesquad.issuetracker.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Service {

	private static final String UPLOADED_IMAGES_DIR = "public/uploaded-images/";

	private final S3Uploader s3Uploader;

	@Transactional
	public String uploadImage(MultipartFile file) {
		ImageFile imageFile = ImageFile.from(file);

		BufferedImage bufferedImage;
		BufferedImage resizedImage;
		try {
			bufferedImage = ImageIO.read(file.getInputStream());
			if (Math.max(bufferedImage.getWidth(), bufferedImage.getHeight()) > 800) {
				resizedImage = Thumbnails.of(bufferedImage)
					.size(800, 800).asBufferedImage();
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				ImageIO.write(resizedImage, Objects.requireNonNull(file.getContentType()).split("/")[1], output);
				ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
				imageFile = new ImageFile(file.getOriginalFilename(), file.getContentType(), input,
					(long)output.toByteArray().length);
			}
		} catch (IOException e) {
			throw new ApplicationException(ErrorCode.NON_IMAGE_FILE);
		}

		// 버킷에 저장할 파일 이름 생성
		String fileName = UPLOADED_IMAGES_DIR + imageFile.getRandomName();
		return s3Uploader.uploadImageToS3(imageFile, fileName);
	}
}
