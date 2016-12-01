package de.frickelbude.es.sandbox.service.io;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.frickelbude.es.sandbox.entity.Component;
import de.frickelbude.es.sandbox.service.EsSandboxException;

/**
 * A simple serializer / deserializer for converting between {@linkplain Component} classes and corresponding JSON
 * representations. The polymorphism in the {@linkplain Component} class hierarchy is handled via custom configuration
 * in the Component class.
 * 
 * @author Ibragim Kuliev
 *
 */
@Service
public class ComponentJsonConverter {

    @Autowired
    private ObjectMapper objectMapper;

    public String toJson(final Component component) {
        try {
            return objectMapper.writeValueAsString(component);
        } catch (JsonProcessingException ex) {
            throw new EsSandboxException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T toModel(final String jsonData) {
        try {
            return (T) objectMapper.readValue(jsonData, Component.class);
        } catch (IOException ex) {
            throw new EsSandboxException(ex);
        }
    }
}
