package laba3.csv;

import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import laba3.Entity;
import laba3.IDataProvider;
import laba3.NullObjectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class DataProviderCsv implements IDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderCsv.class);

    public DataProviderCsv() {
    }
    @Override
    public Entity getById(long id) {
        List<Entity> entities = selectEntities();
        List<Entity> entity;
        try {
            entity = entities.stream().filter(a -> a.getId() == id).collect(Collectors.toList());
            if (entity.isEmpty()){
                throw new NullObjectException("entity is null");
            }
        } catch (NullObjectException e){
            logger.catching(e);
            return null;
        }
//        Entity entity = null;
//        try {
//            entity = entities.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
//        } catch (IndexOutOfBoundsException e) {
//            logger.catching(e);
//        }
//        return entity;
        return entity.get(0);
    }

    @Override
    public List<Entity> selectEntities() {
        List<Entity> beans = null;
        try {
            beans = new CsvToBeanBuilder<Entity>(new FileReader(Constants.CSV_PATH))
                    .withType(Entity.class).build().parse();
        } catch (FileNotFoundException e) {
            logger.catching(e);
        }
        return beans;
    }

    @Override
    public void insert(Entity entity) {
        Writer writer = null;
        File file = new File(Constants.CSV_PATH);
        if ((file.length() != 0) && file.exists()){
            try {
                writer = new FileWriter(file, true);
            } catch (IOException e) {
                logger.catching(e);
            }
            ColumnPositionMappingStrategy<Entity> mappingStrategy = new ColumnPositionMappingStrategy<>();
            mappingStrategy.setType(Entity.class);
            mappingStrategy.setColumnMapping(Constants.ENTITY_ID, Constants.ENTITY_NAME, Constants.ENTITY_SURNAME);
            StatefulBeanToCsv<Entity> beanToCsv = new StatefulBeanToCsvBuilder<Entity>(writer).withMappingStrategy(mappingStrategy).build();
            try {
                beanToCsv.write(entity);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                logger.catching(e);
            }
        } else {
            try {
                writer = new FileWriter(file, true);
            } catch (IOException e) {
                logger.catching(e);
            }
            StatefulBeanToCsv<Entity> beanToCsv = new StatefulBeanToCsvBuilder<Entity>(writer).build();
            try {
                beanToCsv.write(entity);
            } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                logger.catching(e);
            }
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.catching(e);
        }

    }

    @Override
    public void delete(long id) {
        Entity en = getById(id);
        if (en == null){
            return;
        }
        List<Entity> entities = selectEntities();
        entities.removeIf(a -> (a.getId() == id));
        Writer writer = null;
        try {
            writer = new FileWriter(Constants.CSV_PATH);
        } catch (IOException e) {
            logger.catching(e);
        }
        StatefulBeanToCsv<Entity> beanToCsv = new StatefulBeanToCsvBuilder<Entity>(writer).build();
        try {
            beanToCsv.write(entities);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.catching(e);
        }
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            logger.catching(e);
        }
    }

    @Override
    public void update(long id, Entity entity) {
        Entity en = getById(id);
        if (en == null){
            return;
        }
        delete(id);
        insert(entity);
    }

}
