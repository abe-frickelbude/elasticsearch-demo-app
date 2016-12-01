package de.frickelbude.es.sandbox;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;

/**
 * Main application class
 * 
 * @author Ibragim Kuliev
 *
 */
@SpringBootApplication
public class ElasticSearchDemoApplication {

    public static void main(final String[] args) throws Exception {

        SpringApplication.run(ElasticSearchDemoApplication.class, args);
    }
}
