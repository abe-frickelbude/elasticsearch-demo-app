package de.frickelbude.es.sandbox.service.generator;

import java.util.*;
import javax.annotation.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.core.*;
import org.springframework.stereotype.*;
import de.frickelbude.es.sandbox.common.*;
import de.frickelbude.es.sandbox.entity.Component;
import de.frickelbude.es.sandbox.util.*;

/**
 * A factory class that generates various types of {@linkplain Component} with sequential IDs, but otherwise randomly
 * assigned values.
 * 
 * @author Ibragim Kuliev
 *
 */
@Service
public class RandomComponentFactory {

    @Log
    private static Logger log;

    @Autowired
    private ApplicationContext appContext;

    private Map<Class<?>, ComponentBuilder<?>> builderRegistry;
    private List<Class<?>> componentClasses;

    @PostConstruct
    public void init() {
        builderRegistry = new HashMap<>();
        componentClasses = new ArrayList<>();
        for (ComponentBuilder<?> builder : appContext.getBeansOfType(ComponentBuilder.class).values()) {
            addBuilder(builder);
        }
    }

    public List<Class<?>> getComponentClasses() {
        return new ArrayList<>(componentClasses);
    }

    public Component makeComponent() {
        Class<?> componentClass = RandomValueUtils.pickRandomValue(componentClasses);
        ComponentBuilder<?> builder = builderRegistry.get(componentClass);
        return builder.buildComponent();
    }

    /**
     * Registers a builder with the factory. The method uses some Spring black magic to determine the source data type
     * of a builder.
     * 
     * @param builder
     *        a subclass of {@linkplain ComponentBuilder} to be registered.
     */
    private void addBuilder(final ComponentBuilder<?> builder) {

        ResolvableType type = ResolvableType.forClass(builder.getClass()).as(ComponentBuilder.class);
        ResolvableType[] generics = type.getGenerics();
        if (generics.length < 1) {
            throw new IllegalArgumentException("Unable to determine source data type for supplied builder instance!");
        }

        Class<?> sourceType = generics[0].resolve();
        builderRegistry.put(sourceType, builder);
        componentClasses.add(sourceType);
        log.info("Registered component builder for {}", sourceType.getSimpleName());
    }
}
