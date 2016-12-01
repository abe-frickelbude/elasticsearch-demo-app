package de.frickelbude.es.sandbox.service.generator;

import de.frickelbude.es.sandbox.entity.Component;
import de.frickelbude.es.sandbox.entity.Resistor;

/**
 * Base interface for various random component data builders.
 * 
 * Contract: a component builder <strong>must</strong> assign a sequential unique ID to a component (by using the
 * {@linkplain IdGenerator}), but is otherwise free to use random values for a component's fields, assuming
 * they are within valid ranges.
 * 
 * The simple way to fulfill the above requirement is to use the {@linkplain BasicValueFiller} service to fill out the
 * essential fields of a newly generated component at the beginning of buildComponent().
 * 
 * @author Ibragim Kuliev
 *
 * @param <T>
 *        specific component type, e.g. {@linkplain Resistor}.
 */
public interface ComponentBuilder<T extends Component> {

    /**
     * 
     * @return New component instance
     */
    T buildComponent();
}
