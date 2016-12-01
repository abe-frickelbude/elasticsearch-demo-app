package de.frickelbude.es.sandbox.controller;

import org.elasticsearch.action.admin.cluster.health.*;
import org.elasticsearch.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import de.frickelbude.es.sandbox.common.*;

@RestController
public class ManagementController {

    @Log
    private static Logger log;

    @Autowired
    private Client client;

    @RequestMapping("/cluster_status")
    public ClusterHealthResponse getClusterStatus() {

        AdminClient adminClient = client.admin();
        ClusterHealthResponse response = adminClient.cluster().prepareHealth().execute().actionGet();
        log.info("Cluster status: {}", response.getStatus());
        return response;
    }

}
