package marcelocf.janusgraph;

import static org.apache.tinkerpop.gremlin.process.traversal.P.eq;

import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BuiltinQueries {

  ///////////////////
  // Static Block //
  /////////////////

  private static final Logger LOGGER = LoggerFactory.getLogger(BuiltinQueries.class);

  /**
   * Just a simple print method for every property returned from a vertex
   * traversal
   * 
   * @param traversal
   */
  private static void print(GraphTraversal<Vertex, Vertex> traversal) {
    GraphTraversal<Vertex, Map<String, Object>> valueMap = traversal.valueMap(true);
    for (GraphTraversal<Vertex, Map<String, Object>> it = valueMap; it.hasNext();) {
      Map<String, Object> item = it.next();
      for (Object key : item.keySet()) {
        LOGGER.info(" {}: {}", key, item.get(key));
      }
    }
  }

  /**
   * Run every example query, outputting results via @LOGGER
   *
   * @param argv
   * @throws Exception
   */
  public static void main(String[] argv) throws Exception {
    // 1. Create a JanusGraph instance
    JanusGraph graph = JanusGraphFactory.open(Schema.CONFIG_FILE);
    
    // 2. Create a graph traversal source from the JanusGraph instance using the standard, OLTP traversal engine.
    GraphTraversalSource g = graph.traversal();
    
    // 3. Spawn a traversal off the traversal source that determines user by property userName = "testUser0"
    GraphTraversal<Vertex, Vertex> userTraversal = g.V().hasLabel(Schema.USER).has(Schema.USER_NAME, eq("testUser0"));
    
    // 4. Execute the traversal
    print(userTraversal);
    
    LOGGER.info("========================");
    GraphTraversal<Vertex, Vertex> statusTraversal = g.V().hasLabel(Schema.USER).has(Schema.USER_NAME, eq("testUser0")).out(Schema.POSTS);
    print(statusTraversal);
    
    graph.close();
  }
}
