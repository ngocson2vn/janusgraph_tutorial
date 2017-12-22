
package marcelocf.janusgraph;

import java.util.Calendar;
import java.util.Date;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

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

    Vertex users[] = loader.generateUsers(1);
    loader.commit();
    for (Vertex user : users) {
      LOGGER.info("User {} comments:", user.value(Schema.USER_NAME).toString());
      for (Vertex update : loader.generateStatusUpdates(user, 2)) {
        LOGGER.info("     -> {}", update.value(Schema.CONTENT).toString());
      }
      loader.commit();

      // LOGGER.info("User {} follows:", user.value(Schema.USER_NAME).toString());
      // for (Vertex followedUser : loader.generateFollows(user, users, 2)) {
      // LOGGER.info(" -> {}", followedUser.value(Schema.USER_NAME).toString());
      // }
      // loader.commit();
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
      users[i] = addUser("testUser" + i, "Foo" + i, "Bar" + i);
    }

    return users;
  }

  private Vertex[] generateStatusUpdates(Vertex user, int count) {
    Vertex[] updates = new Vertex[count];
    for (int i = 0; i < count; i++) {
      updates[i] = addStatusUpdatew(user, user.id() + "Content" + i);
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
  private Vertex addUser(String userName, String firstName, String lastName) {
    Vertex user = graph.addVertex(Schema.USER);
    user.property(Schema.USER_NAME, userName);
    user.property(Schema.FIRST_NAME, firstName);
    user.property(Schema.LAST_NAME, lastName);
    return user;
  }

  private Vertex addStatusUpdatew(Vertex user, String statusUpdateContent) {
    Vertex statusUpdate = graph.addVertex(Schema.STATUS);
    statusUpdate.property(Schema.CONTENT, statusUpdateContent);
    user.addEdge(Schema.POSTS, statusUpdate, Schema.CREATED_AT, getTimestamp());
    return statusUpdate;
  }

  /**
   * Return a timestamp between 1 month ago and now.
   * 
   * @return
   */
  private Long getTimestamp() {
    return faker.date().between(oneMonthAgo, new Date()).getTime();
  }

}
