package vttp2023.batch3.csf.assessment.cnserver.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.services.NewsService;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/news")
public class NewsController {

	@Autowired
	private NewsService service;

	// TODO: Task 1
	@PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	// @PostMapping(path = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> postNews(
			@RequestPart("image") MultipartFile image,
			@RequestPart("title") String title,
			@RequestPart("description") String description,
			@RequestPart(name = "tags", required = false) String tags) {

		News news = new News();
		news.setTitle(title);
		news.setDescription(description);

		if (tags != null) {
			List<String> tagsList = new ArrayList<>();

			String[] arr = tags.split("^[,]");

			for (String s : arr) {
				tagsList.add(s);
			}

			news.setTags(tagsList);
		}

		try {
			String id = service.postNews(news, image);

			JsonObject json = Json.createObjectBuilder()
					.add("newsId", id)
					.build();

			return ResponseEntity.ok(json.toString());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	// TODO: Task 2
	@GetMapping("/tags")
	public ResponseEntity<String> getTags(@RequestParam("time") Integer time) {

		// get tags from Mongo
		List<TagCount> tList = service.getTags(time);
		JsonArrayBuilder jb = Json.createArrayBuilder();
		for (TagCount tc : tList) {

			JsonObject json = Json.createObjectBuilder()
					.add("tag", tc.tag())
					.add("count", tc.count().toString())
					.build();

			jb.add(json.toString());
		}

		return ResponseEntity.ok(jb.build().toString());
	}

	// TODO: Task 3
	@GetMapping("/{tag}")
	public ResponseEntity<String> getNewsByTag(@PathVariable("tag") String tag) {

		List<News> newsList = service.getNewsByTag(tag);

		JsonArrayBuilder jb = Json.createArrayBuilder();
		for (News n : newsList) {
			JsonObject json = Json.createObjectBuilder()
					.add("title", n.getTitle())
					.add("postDate", String.valueOf(n.getPostDate()))
					.add("image", n.getImage())
					.add("description", n.getDescription())
					.add("tags", n.getTags().toString())
					.build();

			jb.add(json.toString());
		}

		return ResponseEntity.ok(jb.build().toString());
	}
}
