package vttp2023.batch3.csf.assessment.cnserver.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;

@Repository
public class ImageRepository {
	
	@Autowired
	private AmazonS3 s3;

	// TODO: Task 1
	
}
