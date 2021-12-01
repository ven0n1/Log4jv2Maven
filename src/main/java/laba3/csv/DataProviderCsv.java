package laba3.csv;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;
import laba3.DataProvider;
import laba3.Entity;
import laba3.IDataProvider;
import laba3.NullObjectException;
import mongoDB.HistoryContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProviderCsv extends DataProvider {
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
            logger.error(e);
            return null;
        }
//        Entity entity = null;
//        try {
//            entity = entities.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
//        } catch (IndexOutOfBoundsException e) {
//            logger.error();(e);
//        }
//        return entity;
        return entity.get(0);
    }

    @Override
    public List<Entity> selectEntities() {
        return read(Entity.class);
    }

    @Override
    public void insert(Entity entity) {
        File file = new File(Constants.CSV_PATH);
        List<Entity> entityList = new ArrayList<>();
        if ((file.length() != 0) && file.exists()) {
            entityList = selectEntities();
        }
        entityList.add(entity);
        save(entityList);
    }

    @Override
    public void delete(long id) {
        Entity en = getById(id);
        if (en == null){
            return;
        }
        List<Entity> entityList = selectEntities();
        entityList.removeIf(a -> (a.getId() == id));
        save(entityList);
    }

    @Override
    public void update(Entity entity) {
        Entity en = getById(entity.getId());
        if (en == null){
            return;
        }
        delete(entity.getId());
        insert(entity);
    }

    public <T> List<T> read(Class<?> type){
        List<T> entityList = null;
        try {
            entityList = new CsvToBeanBuilder<T>(new FileReader(Constants.CSV_PATH))
                    .withType((Class<? extends T>) type).build().parse();
        } catch (FileNotFoundException e) {
            logger.error(e);
        }
        return entityList;
    }

    public <T> void save(List<T> list){
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(Constants.CSV_PATH));
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(csvWriter).build();
            beanToCsv.write(list);
            csvWriter.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            logger.error(e);
        }
        saveHistory(getClass().getName(), "save(List<Entity> list)", HistoryContent.Status.SUCCESS);
    }
}
