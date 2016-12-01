package de.frickelbude.es.sandbox.base;

import static org.junit.Assert.*;
import org.elasticsearch.action.admin.cluster.health.*;
import org.elasticsearch.client.*;
import org.elasticsearch.cluster.health.*;
import org.junit.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import de.frickelbude.es.sandbox.common.*;

/**
 * This should pass OK if the Spring app context is initialized properly and the ES client connection is available.
 * 
 * @author Ibragim Kuliev
 *
 */
public class ContextInitTest extends BaseEsSandboxTest {

    @Log
    private static Logger log;

    @Autowired
    private Client client;

    @Test
    public void testContextInitOk() {

    }

    @Test
    public void testEsClientOk() {
        AdminClient adminClient = client.admin();
        ClusterHealthResponse response = adminClient.cluster().prepareHealth().execute().actionGet();
        assertNotNull(response);
        assertTrue(response.getStatus().value() <= ClusterHealthStatus.YELLOW.value());
        log.info("Cluster status: {}", response.getStatus());
    }
}
