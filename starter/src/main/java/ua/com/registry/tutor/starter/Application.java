package ua.com.registry.tutor.starter;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Application {

  public static void main(String[] args) {
    MongoCredential credential = MongoCredential.createCredential(
        System.getenv("MONGO_ROOT_USER"),
        "admin",
        System.getenv("MONGO_ROOT_PASS").toCharArray());

    MongoClient client = new MongoClient(
        new ServerAddress(
            System.getenv("MONGO_HOST"), Integer.parseInt(System.getenv("MONGO_PORT"))),
        credential,
        MongoClientOptions.builder().build());

    getUserDefinitions().forEach(user -> {
      MongoDatabase db = client.getDatabase(user.database);

      BasicDBObject command = new BasicDBObject("createUser", user.user)
          .append("pwd", user.password)
          .append("roles", Collections.singletonList(
              new BasicDBObject("role", "readWrite").append("db", user.database)));
      db.runCommand(command);
    });
  }

  private static List<UserDefinition> getUserDefinitions() {
    return Arrays.asList(
        new UserDefinition(System.getenv("REGISTRATION_DB_USER"), System.getenv("REGISTRATION_DB_PASS"), System.getenv("REGISTRATION_DB_NAME")),
        new UserDefinition(System.getenv("NOTIFICATION_DB_USER"), System.getenv("NOTIFICATION_DB_PASS"), System.getenv("NOTIFICATION_DB_NAME"))
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
