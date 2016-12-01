package de.frickelbude.es.sandbox.config;

import java.net.*;
import org.elasticsearch.client.*;
import org.elasticsearch.client.transport.*;
import org.elasticsearch.common.settings.*;
import org.elasticsearch.common.transport.*;
import org.elasticsearch.transport.client.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

/**
 * Configuration for ES transport client.
 * 
 * @author Ibragim Kuliev
 *
 */
@Configuration
public class ElasticSearchConfiguration {

    private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Value("${ES_HOST:localhost}")
    private String hostName;

    @Value("${ES_PORT:9300}")
    private Integer port;

    @Bean
    public Client searchClient() throws Exception {

        log.info("Creating ElasticSearch transport client for {}:{}", hostName, port);
        TransportClient client = null;
        try {

            Settings settings = Settings.builder().put("cluster.name", "es-demo-cluster")
                .put("client.transport.sniff", true).build();

            client = new PreBuiltTransportClient(settings);
            // client = TransportClient.builder().settings(settings).build(); // <-- for ES 2.x
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hostName), port));

        } catch (Exception ex) {
            log.error("Error while creating ES client!", ex);
            throw ex;
        }
        return client;
    }
}
