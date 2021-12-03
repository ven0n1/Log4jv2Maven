package laba3;

import com.google.gson.Gson;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import mongoDB.HistoryContent;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class DataProvider {
    abstract public Entity getById(long id);

    abstract public List<Entity> selectEntities();

    abstract public void insert(Entity entity);

    abstract public void delete(long id);

    abstract public void update(Entity entity);

    public void saveHistory(String className, String methodName, HistoryContent.Status status, String json){
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("history");
            try {
                database.createCollection("historyContent");
            } catch (MongoCommandException ignored) {
            }
            String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
            HistoryContent historyContent = new HistoryContent(className, date, methodName, status, json);
            MongoCollection<Document> collection = database.getCollection("historyContent");
            collection.insertOne(Document.parse(new Gson().toJson(historyContent)));
        }
    }
}
