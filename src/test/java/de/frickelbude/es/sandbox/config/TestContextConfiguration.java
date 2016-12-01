package de.frickelbude.es.sandbox.config;

import org.slf4j.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.*;
import de.frickelbude.es.sandbox.common.*;
import de.frickelbude.es.sandbox.service.generator.*;

/**
 * Spring application context configuration for all integration tests.
 * 
 * @author Ibragim Kuliev
 *
 */
@Configuration
@ComponentScan(basePackages = "de.frickelbude.es.sandbox")
public class TestContextConfiguration {

    private static final Logger log = LoggerFactory.getLogger(TestContextConfiguration.class);

    // By default, the ES transport client connects to localhost:9300
    // private static final String HOST_NAME = "192.168.2.124";
    private static final String HOST_NAME = "localhost";
    private static final int HOST_PORT = 9300;

    @Bean
    public LoggerPostProcessor loggerPostProcessor() {
        LoggerPostProcessor bean = new LoggerPostProcessor();
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean
    public IdGenerator sequenceGenerator() {
        return new IdGenerator();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        return objectMapper;
    }

    // /**
    // * Shared ES client.
    // *
    // */
    // @Bean
    // public Client searchClient() throws Exception {
    //
    // log.info("Creating ElasticSearch transport client for {}:{}", HOST_NAME, HOST_PORT);
    // TransportClient client = null;
    // try {
    //
    // Settings settings = Settings.builder().put("cluster.name", "es-demo-cluster")
    // .put("client.transport.sniff", true).build();
    //
    // client = new PreBuiltTransportClient(settings);
    // client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(HOST_NAME), HOST_PORT));
    //
    // } catch (Exception ex) {
    // log.error("Error while creating ES client!", ex);
    // throw ex;
    // }
    // return client;
    // }
}
