package laba3.xml;

import laba3.Entity;
import laba3.IDataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Constants;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProviderXml implements IDataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderXml.class);

    public DataProviderXml() {
    }

    @Override
    public Entity getById(long id) {
        List<Entity> entities = selectEntities();
        Entity entity = null;
        try {
            entity = entities.stream().filter(a -> a.getId() == id).collect(Collectors.toList()).get(0);
        } catch (IndexOutOfBoundsException e) {
            logger.catching(e);
        }
        return entity;
    }

    @Override
    public List<Entity> selectEntities() {
        Entities entities = new Entities();
        try {
            JAXBContext context = JAXBContext.newInstance(Entities.class);
            entities = (Entities) context.createUnmarshaller().unmarshal(new File(Constants.XML_PATH));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return entities.getEntities();
    }

    @Override
    public void insert(Entity entity) {
        File file = new File(Constants.XML_PATH);
        JAXBContext context;
        Entities entities = new Entities();
        List<Entity> entityList = new ArrayList<>();
        Marshaller marshaller;
        if(file.exists()) {
            try {
                context = JAXBContext.newInstance(Entities.class);
                // Read XML
                Unmarshaller unmarshaller = context.createUnmarshaller();
                entities = (Entities) unmarshaller.unmarshal(new File(Constants.XML_PATH));
                entityList = entities.getEntities();
                // Add new entity
                entityList.add(entity);
                entities.setEntities(entityList);
                // Save new entities
                marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(entities, new File(Constants.XML_PATH));
            } catch (JAXBException e) {
                logger.catching(e);
            }
        } else {
            try {
                context = JAXBContext.newInstance(Entities.class);
                entityList.add(entity);
                entities.setEntities(entityList);
                marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(entities, new File(Constants.XML_PATH));
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(long id) {
        Entity en = getById(id);
        if (en == null){
            return;
        }
        JAXBContext context;
        Entities entities = new Entities();
        Marshaller marshaller;
        List<Entity> entityList = selectEntities();
        entityList.removeIf(a -> (a.getId() == id));
        entities.setEntities(entityList);
        // Save new entities
        try {
            context = JAXBContext.newInstance(Entities.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(entities, new File(Constants.XML_PATH));
        } catch (JAXBException e) {
            logger.catching(e);
        }
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
}
