package org.example;

import laba3.Entity;
import laba3.IDataProvider;
import laba3.csv.DataProviderCsv;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private static final Logger logger = LogManager.getLogger(AppTest.class);
    IDataProvider dp = new DataProviderCsv();
    /**
     * Rigorous Test :-)
     */


    @Test
    public void testInsert(){
        Entity entity = new Entity(1, "name", "surname");
        Entity entity1 = new Entity(2, "name1", "surname1");
        dp.insert(entity);
        dp.insert(entity1);
    }

    @Test
    public void testGetById(){
        Entity entity = new Entity(1, "name", "surname");
        dp.insert(entity);
        Entity byId = dp.getById(1);
        assertEquals(entity, byId);
    }

    @Test
    public void testSelect(){
        Entity entity = new Entity(1, "name", "surname");
        dp.insert(entity);
        List<Entity> entityList = dp.selectEntities();
        assertEquals(entity, entityList.get(entityList.size()-1));
    }

    @Test
    public void testUpdate(){
        Entity entity = new Entity(2, "name1", "surname1");
        Entity newEntity = new Entity(99, "name98", "surname98");
        dp.insert(entity);
        dp.update(2, newEntity);
        Entity byId = dp.getById(99);
        assertEquals(newEntity, byId);
    }

/*
    @Test
    public void test(){
        testInsert();
        testGetById();
        testSelect();
        testUpdate();
        testDelete();
    }

 */
}
