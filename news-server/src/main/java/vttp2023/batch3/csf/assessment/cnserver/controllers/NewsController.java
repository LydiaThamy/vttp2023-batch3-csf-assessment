package vttp2023.batch3.csf.assessment.cnserver.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
// @RequestMapping("/api/news")
public class NewsController {

	@Autowired
	private NewsService service;

	// TODO: Task 1
	@PostMapping(path = "/api/news/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// @PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postNews(
			@RequestPart MultipartFile photo,
			@RequestPart String title,
			@RequestPart String description,
			@RequestPart List<String> tags) {

		News news = new News();
		news.setTitle(title);
		news.setDescription(description);
		news.setTags(tags);

		try {
			String id = service.postNews(news, photo);

			JsonObject json = Json.createObjectBuilder()
					.add("newsId", id)
					.build();

			return ResponseEntity.ok(json.toString());
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// TODO: Task 2

	// TODO: Task 3

}
