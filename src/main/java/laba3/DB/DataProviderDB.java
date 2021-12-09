package laba3.DB;

import laba3.DataProvider;
import laba3.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataProviderDB extends DataProvider {
    private static final Logger logger = LogManager.getLogger(DataProviderDB.class);
    // JDBC URL, username and password of MySQL server
    public static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/";
    public static final String dbName = "q";
    private static final String user = "root";
    private static final String password = "qwerty";

    // JDBC variables for opening and managing connection
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static final String createTable = "CREATE TABLE IF NOT EXISTS Entities (" +
            "id SERIAL, " +
            "name TEXT, " +
            "surname TEXT);";
    public static final String insert = "INSERT INTO Entities(id, name, surname) VALUES ('%s', '%s', '%s');";
    public static final String getId = "SELECT * FROM Entities WHERE id = %d;";
    public static final String selectAll = "SELECT * FROM Entities;";
    public static final String delete = "DELETE FROM Entities WHERE id = %d;";
    public static final String update = "UPDATE Entities SET name = '%s', surname = '%s' WHERE id = %d;";
    @Override
    public Entity getById(long id) {
        Entity entity = new Entity();
        statement = getStatement();
        try {
            resultSet = statement.executeQuery(String.format(getId, id));
            resultSet.next();
            entity.setId(resultSet.getLong(1));
            entity.setName(resultSet.getString(2));
            entity.setSurname(resultSet.getString(3));
        } catch (SQLException e) {
            logger.error(e);
        }
        return entity;
    }

    @Override
    public List<Entity> selectEntities() {
        List<Entity> entities = new ArrayList<>();
        statement = getStatement();
        try {
            resultSet = statement.executeQuery(selectAll);
            while (resultSet.next()){
                Entity entity = new Entity(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3));
                entities.add(entity);
            }
        } catch (SQLException e) {
            logger.error(e);
        }
        return entities;
    }

    @Override
    public void insert(Entity entity) {
        statement = getStatement();
        try {
            statement.execute(String.format(insert, entity.getId(), entity.getName(), entity.getSurname()));
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void delete(long id) {
        statement = getStatement();
        try {
            statement.execute(String.format(delete, id));
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    @Override
    public void update(Entity entity) {
//        Entity en = getById(entity.getId());
//        if (en == null){
//            return;
//        }
//        delete(entity.getId());
//        insert(entity);
        statement = getStatement();
        try {
            statement.execute(String.format(update, entity.getName(), entity.getSurname(), entity.getId()));
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    public static Statement getStatement() {
        Statement stmt = null;
        try {
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                logger.error(e);
            }
            connection = DriverManager.getConnection(url+dbName, user, password);
            stmt = connection.createStatement();
            stmt.execute(createTable);
        } catch (SQLException e) {
            logger.error(e);
        }
        return stmt;
    }
}
