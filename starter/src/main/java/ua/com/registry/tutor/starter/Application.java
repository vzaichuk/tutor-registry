package ua.com.registry.tutor.starter;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Application {

  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    initMongo();
    initMysql();
  }

  private static void initMongo() {
    System.out.println("[Init Mongo]");
    MongoCredential credential = MongoCredential.createCredential(
        System.getenv("MONGO_ROOT_USER"),
        "admin",
        System.getenv("MONGO_ROOT_PASS").toCharArray());

    MongoClient client = new MongoClient(
        new ServerAddress(
            System.getenv("MONGO_HOST"), Integer.parseInt(System.getenv("MONGO_PORT"))),
        credential,
        MongoClientOptions.builder().build());

    getMongoUserDefinitions().forEach(user -> {
      MongoDatabase db = client.getDatabase(user.database);

      System.out.println("Create user " + user.user);
      BasicDBObject command = new BasicDBObject("createUser", user.user)
          .append("pwd", user.password)
          .append("roles", Collections.singletonList(
              new BasicDBObject("role", "readWrite").append("db", user.database)));
      try {
        db.runCommand(command);
      } catch (Exception ignore) {
      }
    });
  }

  private static void initMysql() throws ClassNotFoundException, SQLException {
    System.out.println("[Init MySQL]");
    Class.forName("com.mysql.cj.jdbc.Driver");

    Connection connect = DriverManager
        .getConnection("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":"
            + System.getenv("MYSQL_PORT") + "/?"
            + "user=root&password=" + System.getenv("MYSQL_ROOT_PASS"));

    Statement statement = connect.createStatement();

    getMysqlUserDefinitions().forEach(user -> {
      try {
        // Create database.
        System.out.println("Create database " + user.database);
        statement.execute(
            "CREATE DATABASE IF NOT EXISTS " + user.database + " DEFAULT CHARSET UTF8MB4");

        // Create user.
        System.out.println("Create user " + user.user);
        statement.execute(
            "CREATE USER IF NOT EXISTS '"
                + user.user + "'@'%' IDENTIFIED BY '" + user.password + "'");

        // Grant privileges.
        System.out.println(
            "Granting privileges on database " + user.database + " to user " + user.user);
        statement.execute("GRANT ALL ON `" + user.database + "`.* TO '"+user.user+"'@'%'");
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
    });
  }

  private static List<UserDefinition> getMongoUserDefinitions() {
    return Arrays.asList(
        new UserDefinition(System.getenv("REGISTRATION_DB_USER"), System.getenv("REGISTRATION_DB_PASS"), System.getenv("REGISTRATION_DB_NAME")),
        new UserDefinition(System.getenv("NOTIFICATION_DB_USER"), System.getenv("NOTIFICATION_DB_PASS"), System.getenv("NOTIFICATION_DB_NAME"))
    );
  }

  private static List<UserDefinition> getMysqlUserDefinitions() {
    return Arrays.asList(
        new UserDefinition(System.getenv("AUTHENTICATION_DB_USER"), System.getenv("AUTHENTICATION_DB_PASS"), System.getenv("AUTHENTICATION_DB_NAME")),
        new UserDefinition(System.getenv("ACCOUNT_DB_USER"), System.getenv("ACCOUNT_DB_PASS"), System.getenv("ACCOUNT_DB_NAME"))
    );
  }

  private static class UserDefinition {

    public UserDefinition(String user, String password, String database) {
      this.user = user;
      this.password = password;
      this.database = database;
    }

    public String user;
    public String password;
    public String database;
  }
}
