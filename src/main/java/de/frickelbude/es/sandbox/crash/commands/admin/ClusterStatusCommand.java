package de.frickelbude.es.sandbox.crash.commands.admin;

import org.elasticsearch.action.admin.cluster.health.*;
import org.elasticsearch.client.*;
import org.elasticsearch.cluster.health.*;
import org.springframework.beans.factory.annotation.*;

/**
 * This doesn't work yet!
 * 
 * @author Ibragim Kuliev
 *
 */
// @Component
public class ClusterStatusCommand /* extends BaseCommand */ {

    @Autowired
    private Client client;

    //
    // @Command
    // @Named("cluster-health")
    // @Usage("Show current ES cluster health status")
    public ClusterHealthStatus getClusterStatus() {
        AdminClient adminClient = client.admin();
        ClusterHealthResponse response = adminClient.cluster().prepareHealth().execute().actionGet();
        return response.getStatus();
    }
}
