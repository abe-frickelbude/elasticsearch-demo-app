package de.frickelbude.es.sandbox.config;

import org.springframework.context.annotation.*;
import org.springframework.core.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.joda.*;
import de.frickelbude.es.sandbox.common.*;
import de.frickelbude.es.sandbox.service.generator.*;

/**
 * Configuration for the various application components.
 * 
 * @author Ibragim Kuliev
 *
 */
@Configuration
@PropertySource("classpath:app_configuration.yml")
public class ApplicationConfiguration {

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
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        return objectMapper;
    }
}
