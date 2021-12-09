package org.example;

import laba3.DB.DataProviderDB;
import laba3.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MainTest {
    private static final Logger logger = LogManager.getLogger(MainTest.class);
    DataProviderDB dp = new DataProviderDB();
    Entity entity1 = new Entity( 1, "name 1", "surname 1");
    Entity entity2 = new Entity( 2, "name 2", "surname 3");
    Entity entity3 = new Entity( 3, "name 3", "surname 3");

    @Before
    public void initTestData(){
        dp.insert(entity1);
        dp.insert(entity2);
        dp.insert(entity3);
    }

    @Test
    public void positiveGetById(){
        Entity byId = dp.getById(entity1.getId());
        assertEquals(entity1.getId(), byId.getId());
    }

    @Test
    public void negativeGetById(){
        Entity byId = dp.getById(10);
        assertNotEquals(entity1.getId(), byId.getId());
    }

    @Test
    public void positiveSelect(){
        List<Entity> entities;
        entities = dp.selectEntities();
        assertEquals(3, entities.size());
    }

    @Test
    public void negativeSelect(){
    }

    @Test
    public void positiveUpdate(){
        Entity newEntity = new Entity(3,"updated name", "updated surname");
        dp.update(newEntity);
        assertEquals(newEntity, dp.getById(newEntity.getId()));
    }

    @Test
    public void negativeUpdate(){
        Entity newEntity = new Entity(4, "updated name", "updated surname");
        dp.update(newEntity);
        assertNotEquals(newEntity, dp.getById(newEntity.getId()));
    }

    @Test
    public void positiveDelete(){
        Entity deleted = new Entity();
        dp.delete(2);
        assertEquals(deleted, dp.getById(2));
    }

    @Test
    public void negativeDelete(){
    }

//    @Test
    public void clear(){
        dp.clear();
    }
}
