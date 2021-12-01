package laba3;

import java.util.List;

public interface IDataProvider {
    Entity getById(long id);
    List<Entity> selectEntities();
    void insert(Entity entity);
    void delete(long id);
    void update(Entity entity);
}
