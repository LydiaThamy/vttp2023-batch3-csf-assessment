package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	// TODO: Task 1
	public String postImage(MultipartFile image) {
		
		String id = UUID.randomUUID().toString().substring(0, 8);

		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(image.getContentType());
			metadata.setContentLength(image.getSize());

			PutObjectRequest putReq = new PutObjectRequest("vttp", "image/%s".formatted(id), image.getInputStream(), metadata);
			putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
			s3.putObject(putReq);

        } catch (IOException e) {
            e.printStackTrace();
        }

        String imageUrl = UriComponentsBuilder
            .fromUriString("https://vttp.sgp1.digitaloceanspaces.com/image")
                .pathSegment(id)
                .toUriString();
                
        return imageUrl;
	}
}
