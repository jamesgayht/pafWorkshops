import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UnwindOptions;
import org.bson.Document;

MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
MongoDatabase database = mongoClient.getDatabase("mydb");

List<Document> pipeline = Arrays.asList(
Aggregates.match(Filters.regex("name", "kill", "i")),
Aggregates.lookup("comments", "gid", "gid", "reviews"),
Aggregates.unwind("$reviews", new UnwindOptions().preserveNullAndEmptyArrays(true)),
Aggregates.project(
Projections.fields(
Projections.excludeId(),
Projections.include("gid", "name", "reviews.rating", "reviews.user", "reviews.c_text", "reviews.c_id")
)
),
Aggregates.sort(Sorts.ascending("name")),
Aggregates.limit(10)
);

MongoCursor<Document> cursor = database.getCollection("games").aggregate(pipeline).iterator();

while (cursor.hasNext()) {
Document result = cursor.next();
System.out.println(result.toJson());
}

mongoClient.close();