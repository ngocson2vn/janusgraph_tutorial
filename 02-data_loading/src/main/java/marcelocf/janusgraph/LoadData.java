
package marcelocf.janusgraph;

import com.github.javafaker.Faker;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * LoadData creation for our example graph db
 */
public class LoadData {

  ////////////////////////
  // Static Attributes //
  //////////////////////

  /**
   * It is usually good practice to output messages using a logging framework.
   * <br/>
   * Here we just use the same as JanusGraph.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(LoadData.class);

  /////////////////////
  // Static Methods //
  ///////////////////

  /**
   * The main code basically instantiate its own class and call individual
   * methods.
   * 
   * @param argv
   */
  public static void main(String[] argv) {
    LoadData loader = new LoadData(Schema.CONFIG_FILE);

    Vertex users[] = loader.generateUsers(4);
    loader.commit();
    for (Vertex user : users) {
      LOGGER.info("User {} comments:", user.value(Schema.USER_NAME).toString());
      for (Vertex update : loader.generateStatusUpdates(user, 2)) {
        LOGGER.info("     -> {}", update.value(Schema.CONTENT).toString());
      }
      loader.commit();

      LOGGER.info("User {} follows:", user.value(Schema.USER_NAME).toString());
      for (Vertex followedUser : loader.generateFollows(user, users, 2)) {
        LOGGER.info("     -> {}", followedUser.value(Schema.USER_NAME).toString());
      }
      loader.commit();
    }

    loader.close();
  }

  //////////////////////////
  // Instance Attributes //
  ////////////////////////

  private final JanusGraph graph;

  private final Faker faker;
  private final Date oneMonthAgo;

  /**
   * Initialize the graph connection.
   *
   * @param configFile
   */
  public LoadData(String configFile) {
    graph = JanusGraphFactory.open(configFile);
    faker = new Faker();

    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.MONTH, -1);
    oneMonthAgo = cal.getTime();
  }

  private void commit() {
    graph.tx().commit();
  }

  /**
   * Commit the current transaction and close the graph.
   */
  private void close() {
    commit();
    graph.close();
  }

  private Vertex[] generateUsers(int count) {
    Vertex[] users = new Vertex[count];

    for (int i = 0; i < count; i++) {
      users[i] = addUser("testUser" + i, "Dummy" + i);
    }

    return users;
  }

  private Vertex[] generateStatusUpdates(Vertex user, int count) {
    Vertex[] updates = new Vertex[count];
    for (int i = 0; i < count; i++) {
      updates[i] = addStatusUpdatew(user, getContent());
    }
    return updates;
  }

  /**
   * Add a user vertex
   * 
   * @param userName
   *          username for this user
   * @return the created vertex
   */
  private Vertex addUser(String userName, String firstName) {
    Vertex user = graph.addVertex(Schema.USER);
    user.property(Schema.USER_NAME, userName);
    user.property(Schema.FIRST_NAME, firstName);
    return user;
  }

  private Vertex addStatusUpdatew(Vertex user, String statusUpdateContent) {
    Vertex statusUpdate = graph.addVertex(Schema.STATUS_UPDATE);
    statusUpdate.property(Schema.CONTENT, statusUpdateContent);
    user.addEdge(Schema.POSTS, statusUpdate, Schema.CREATED_AT, getTimestamp());
    return statusUpdate;
  }

  private Vertex[] generateFollows(Vertex forUser, Vertex[] users, int count) {
    Vertex[] followedUsers = new Vertex[count];

    List<Integer> oldIndexes = new ArrayList<>();
    long userId = Long.parseLong(forUser.id().toString());
    
    for (int i = 0; i < count; i++) {
      int idx = 0;
      do {
        idx = (int)(Math.random() * users.length);
        followedUsers[i] = users[idx];
      } while (userId == Long.parseLong(users[idx].id().toString()) || oldIndexes.contains(idx));
      
      oldIndexes.add(idx);
      
      Edge follows = forUser.addEdge(Schema.FOLLOWS, followedUsers[i], Schema.CREATED_AT, getTimestamp());
    }
    
    return followedUsers;
  }

  /**
   * Return a timestamp between 1 month ago and now.
   * 
   * @return
   */
  private Long getTimestamp() {
    return faker.date().between(oneMonthAgo, new Date()).getTime();
  }

  private String getContent() {
    return faker.chuckNorris().fact();
  }

}
