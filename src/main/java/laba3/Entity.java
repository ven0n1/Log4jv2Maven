package laba3;

import com.opencsv.bean.CsvBindByName;
import org.example.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
@XmlRootElement(name = Constants.ENTITY)
@XmlAccessorType(XmlAccessType.FIELD)
public class Entity {
    @XmlElement(name = Constants.ENTITY_ID)
    @CsvBindByName(column = Constants.ENTITY_ID)
    long id;
    @XmlElement(name = Constants.ENTITY_NAME)
    @CsvBindByName(column = Constants.ENTITY_NAME)
    String name;
    @XmlElement(name = Constants.ENTITY_SURNAME)
    @CsvBindByName(column = Constants.ENTITY_SURNAME)
    String surname;

    public Entity(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public Entity() {
    }

    public Entity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id && Objects.equals(name, entity.name) && Objects.equals(surname, entity.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }
}
