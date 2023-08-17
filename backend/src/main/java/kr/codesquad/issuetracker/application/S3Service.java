package kr.codesquad.issuetracker.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.codesquad.issuetracker.application.s3.S3Uploader;
import kr.codesquad.issuetracker.domain.ImageFile;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class S3Service {

	private static final String UPLOADED_IMAGES_DIR = "public/uploaded-images/";

	private final S3Uploader s3Uploader;

	@Transactional
	public String uploadImage(MultipartFile file) {
		ImageFile imageFile = ImageFile.from(file);

		// 버킷에 저장할 파일 이름 생성
		String fileName = UPLOADED_IMAGES_DIR + imageFile.getRandomName();
		return s3Uploader.uploadImageToS3(imageFile, fileName);
	}
}
