package org.example;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import laba3.DB.DataProviderDB;
import laba3.DataProvider;
import laba3.Entity;
import laba3.csv.DataProviderCsv;
import mongoDB.HistoryContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger logger = LogManager.getLogger(AppTest.class);
//    IDataProvider dp = new DataProviderXml();
    DataProvider dp = new DataProviderDB();
    /**
     * Rigorous Test :-)
     */


    @Test
    public void testInsert(){
        Entity entity = new Entity("nameInsert", "surnameInsert");
        dp.insert(entity);
    }

    @Test
    public void testGetById(){
        Entity entity = new Entity("nameGetById", "surnameGetById");
        dp.insert(entity);
        Entity byId = dp.getById(entity.getId());
        assertEquals(entity, byId);
    }

    @Test
    public void testSelect(){
        Entity entity = new Entity("nameSelect", "surnameSelect");
        dp.insert(entity);
        List<Entity> entityList = dp.selectEntities();
        assertEquals(entity, entityList.get(entityList.size()-1));
    }

    @Test
    public void testUpdate(){
        Entity entity = new Entity("nameUpdate1", "surnameUpdate1");
        Entity newEntity = new Entity(entity.getId(), "nameUpdate98", "surnameUpdate98");
        dp.insert(entity);
        dp.update(newEntity);
        Entity byId = dp.getById(newEntity.getId());
        assertEquals(newEntity, byId);
    }

    @Test
    public void testDelete(){
        Entity entity = new Entity("nameDelete", "surnameDelete");
        dp.insert(entity);
        dp.delete(entity.getId());
    }

//    @Test
//    public void in(){
//        try (var mongoClient = MongoClients.create()) {
//            // use test
//            var database = mongoClient.getDatabase("entities");
//            logger.info(database.getName());
//            var entitiesCollection = database.getCollection("entities");
//            BasicDBObject document = new BasicDBObject();
//            document.put("name", "Shubham");
//            document.put("company", "Baeldung");
//            entitiesCollection. .insert(document);
//            entitiesCollection.listIndexes().forEach((Consumer<Document>) logger::info);
//
//        }
//    }

//    @Test
    public void testCreateMongo(){
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            logger.info("Insert test");
            var database = mongoClient.getDatabase("history");
            try {
                database.createCollection("historyContent");
            } catch (MongoCommandException e) {
                logger.info("Collection already exists");
            }
            String date = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(new Date());
            HistoryContent historyContent = new HistoryContent(getClass().toString(), date, "testCreateMongo()", HistoryContent.Status.SUCCESS, "asd");
            MongoCollection<Document> collection = database.getCollection("historyContent");
            Gson gson = new Gson();
            collection.insertOne(Document.parse(gson.toJson(historyContent)));
        }
    }

//    @Test
    public void testReadMongo(){
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("history");
            MongoCollection<Document> collection = database.getCollection("historyContent");
            logger.info("Read test");
            HistoryContent historyContent = new HistoryContent();
            try (MongoCursor<Document> cur = collection.find().iterator()) {
                while (cur.hasNext()){
                    var doc = cur.next();
                    Gson gson = new Gson();
                    JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);
                    String json = doc.toJson(writerSettings);
//                    logger.info(json);
                    historyContent = gson.fromJson(json, HistoryContent.class);
                    logger.info(historyContent);
                }
            }
        }
    }


//    @Test
    public void as(){
        try (var mongoClient = MongoClients.create("mongodb://localhost:27017")) {

            var database = mongoClient.getDatabase("javaguides");

            try {
                database.createCollection("users");
                logger.info("Collection created successfully");
            } catch (MongoCommandException e) {
                database.getCollection("users").drop();
            }

            var docs = new ArrayList<Document>();

            MongoCollection< Document > collection = database.getCollection("users");

            var d1 = new Document("_id", 1);
            d1.append("_firstName", "Ramesh");
            d1.append("_lastName", "Fadatare");
            docs.add(d1);

            var d2 = new Document("_id", 2);
            d2.append("_firstName", "Tony");
            d2.append("_lastName", "Stark");
            docs.add(d2);

            var d3 = new Document("_id", 3);
            d3.append("_firstName", "Tom");
            d3.append("_lastName", "Cruise");
            docs.add(d3);

            var d4 = new Document("_id", 4);
            d4.append("_firstName", "Amir");
            d4.append("_lastName", "Khan");
            docs.add(d4);

            var d5 = new Document("_id", 5);
            d5.append("_firstName", "Umesh");
            d5.append("_lastName", "Fadatare");
            docs.add(d5);

            collection.insertMany(docs);
            try (MongoCursor<Document> cur = collection.find().iterator()) {

                while (cur.hasNext()) {

                    var doc = cur.next();
                    var cars = new ArrayList<>(doc.values());

                    logger.info(cars.get(1).toString() + cars.get(2));
                }
            }
        }

    }

//    @Test
    public void a(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        logger.info(list);
    }

}
