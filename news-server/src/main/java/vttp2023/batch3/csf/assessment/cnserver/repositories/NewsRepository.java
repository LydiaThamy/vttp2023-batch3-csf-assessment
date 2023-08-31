package vttp2023.batch3.csf.assessment.cnserver.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationPipeline;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import vttp2023.batch3.csf.assessment.cnserver.models.News;
import vttp2023.batch3.csf.assessment.cnserver.models.TagCount;

@Repository
public class NewsRepository {

	@Autowired
	private MongoTemplate template;

	// TODO: Task 1
	// Write the native Mongo query in the comment above the method
	/*
		 * db.news.insert({
		 * postDate: 1693458442130,
		 * title: 'Hello',
		 * desription: 'saying hi',
		 * image: 'https://vttp.sgp1.digitaloceanspaces.com/image/3243c248',
		 * tags: ['greetings', 'hi']
		 * })
		 */
	public String postNews(News news) {

		ObjectId id = new ObjectId();

		Document doc = new Document("_id", id)
    	.append("postDate", news.getPostDate())
		.append("title", news.getTitle())
		.append("description", news.getDescription())
		.append("image", news.getImage())
		.append("tags", Arrays.asList(news.getTags().toArray()));
		
		template.insert(doc, "news");

		return id.toString();
	}

	// TODO: Task 2
	// Write the native Mongo query in the comment above the method
	/*db.news.aggregate([
    { "$match": {
        "postDate": { "$gte": 000},
        "tags": { "$exists": true }
    }},
    { "$unwind": "$tags" },
    { "$group": {
        "_id": "$tags",
        "count": { "$sum": 1 }
    }},
    { "$sort": {"count": -1, "_id": 1}},
    { "$limit": 10 }
]) */
	public List<Document> getTags(Integer time) {
		
		long requestedTime = System.currentTimeMillis() - (time * 60000);

		MatchOperation match = Aggregation.match(
			Criteria.where("postDate").gte(requestedTime)
			.and("tags").exists(true)
		);

		AggregationOperation unwind = Aggregation.unwind("tags");
		GroupOperation group = Aggregation.group("tags")
			.count().as("count");
		
		SortOperation sort = Aggregation.sort(
			Sort.by(Direction.DESC, "count")
			.and(Sort.by(Direction.ASC, "_id"))
		);

		LimitOperation limit = Aggregation.limit(10);

		Aggregation pipeline = Aggregation.newAggregation(match, unwind, group, sort, limit);
		return template.aggregate(pipeline, "news", Document.class).getMappedResults();
	}


	// TODO: Task 3
	// Write the native Mongo query in the comment above the method

}
