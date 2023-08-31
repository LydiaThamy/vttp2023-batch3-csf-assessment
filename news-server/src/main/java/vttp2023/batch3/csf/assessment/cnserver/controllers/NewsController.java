package vttp2023.batch3.csf.assessment.cnserver.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/news")
public class NewsController {

	// TODO: Task 1
	@PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> savePost(
			@RequestPart MultipartFile photo,
			@RequestPart String title,
			@RequestPart String description,
			@RequestPart List<String> tags) {

		System.out.println(title);
		System.out.println(description);
		System.out.println(tags.toString());
		return ResponseEntity.ok(null);
	}

	// TODO: Task 2

	// TODO: Task 3

}
