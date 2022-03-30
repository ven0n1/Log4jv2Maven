package org.example;

import laba3.IDataProvider;
import laba3.csv.DataProviderCsv;
import laba3.Entity;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);
    static IDataProvider dpc;
    static Entity entity;
    static List<Entity> entities;

    public App() {
        logger.debug("App()[0]: starting application.........");
    }

    public static void main( String[] args ) {
        User user = new User(1, "first");
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        List<Integer> list = new ArrayList<>();
        list = users.stream().map(User::getId).collect(Collectors.toList());
        System.out.println(list);
        /*
        logger.trace("We've just greeted the user!");
        logger.debug("We've just greeted the user!");
        logger.info("We've just greeted the user!");
        logger.warn("We've just greeted the user!");
        logger.error("We've just greeted the user!");
        logger.fatal("We've just greeted the user!");
        logBasicSystemInfo();
         */

        /*
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/enviroment.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(properties.getProperty("home"));
         */

        /*
        String key = "home";
        String properties = null;
        try {
            properties = ConfigurationUtil.getConfigurationEntry(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(properties);
         */
//        dpc = new DataProviderCsv();
//        int choice = 2;
//        int id = 1;
//        switch (choice){
//            case 1:
//                for (int i = 0; i < 10; i++) {
//                    entity = new Entity(i, "name " + i, "surname " + i);
//                    dpc.insert(entity);
//                }
//                break;
//            case 3:
//                entity = dpc.getById(id);
//                logger.info(entity);
//                break;
//            case 4:
//                dpc.delete(id);
//                entities = dpc.selectEntities();
//                entities.forEach(logger::info);
//                break;
//            case 5:
//                entity = new Entity(999, "asdasd", "gjghj");
//                dpc.update(entity);
//            case 2:
//                entities = dpc.selectEntities();
//                entities.forEach(logger::info);
//                break;
//        }
    }

    private static void logBasicSystemInfo() {
        logger.info("Launching the application...");
        logger.info(
                "Operating System: " + System.getProperty("os.name") + " "
                        + System.getProperty("os.version")
        );
        logger.info("JRE: " + System.getProperty("java.version"));
        logger.info("Java Launched From: " + System.getProperty("java.home"));
        logger.info("Class Path: " + System.getProperty("java.class.path"));
        logger.info("Library Path: " + System.getProperty("java.library.path"));
        logger.info("User Home Directory: " + System.getProperty("user.home"));
        logger.info("User Working Directory: " + System.getProperty("user.dir"));
        logger.info("Test INFO logging.");
    }
}

class User {
    int id;
    String name;

    User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    int getId() {
        return this.id;
    }
}
