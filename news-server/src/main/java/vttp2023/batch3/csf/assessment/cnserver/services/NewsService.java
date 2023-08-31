package vttp2023.batch3.csf.assessment.cnserver.services;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;
import vttp2023.batch3.csf.assessment.cnserver.repositories.ImageRepository;
import vttp2023.batch3.csf.assessment.cnserver.repositories.NewsRepository;

@Service
public class NewsService {

	@Autowired
	private ImageRepository imageRepo;

	@Autowired
	private NewsRepository newsRepo;
	
	// TODO: Task 1
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns the news id
	public String postNews(News news, MultipartFile photo) throws IOException {

		// set date
		long date = System.currentTimeMillis();
		news.setPostDate(date);

		// set image URL
		String imageUrl = imageRepo.postImage(photo);
		news.setImage(imageUrl);

		// save post in redis
		String id = newsRepo.postNews(news);
		return id;
	}
	 
	// TODO: Task 2
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of tags and their associated count
	public List<TagCount> getTags(Integer time) {

		List<TagCount> tList = new LinkedList<>();
		List<Document> docs = newsRepo.getTags(time);
		
		for (Document d: docs) {
			tList.add(
				new TagCount(d.getString("_id"), d.getInteger("count"))
			);
		}

		return tList;
	}

	// TODO: Task 3
	// Do not change the method name and the return type
	// You may add any number of parameters
	// Returns a list of news
	public List<News> getNewsByTag(String tag, Integer time) {
		System.out.println("getting news in service...");
		List<News> newsList = new LinkedList<>();
		List<Document> docs = newsRepo.getNewsById(tag, time);

		for (Document d : docs) {
			News n = new News();
			n.setDescription(d.getString("description"));
			n.setImage(d.getString("image"));
			n.setPostDate(d.getLong("postDate"));
			n.setTags(d.getList("tags", String.class));
			n.setTitle(d.getString("title"));
			newsList.add(n);
		}
		return newsList;
	}
	
}
