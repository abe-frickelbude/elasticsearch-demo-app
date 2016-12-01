package de.frickelbude.es.sandbox.entity;

/**
 * Base class for semiconductors, further specialization is in subtypes.
 * 
 * @author Ibragim Kuliev
 *
 */
public abstract class SemiconductorDevice extends Component {

    public static final String CATEGORY = "semiconductor";

    public SemiconductorDevice(final String type) {
        super(type, CATEGORY);
    }
}
