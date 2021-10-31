package laba3.xml;

import laba3.Entity;
import org.example.Constants;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = Constants.ENTITIES)
public class Entities {
    private List<Entity> entities;

    public List<Entity> getEntities() {
        return entities;
    }
    @XmlElement(name = Constants.ENTITY)
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        return entities.toString();
    }
}
