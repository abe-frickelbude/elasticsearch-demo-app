package de.frickelbude.es.sandbox.entity;

public abstract class PassiveDevice extends Component {

    public static final String CATEGORY = "passive";

    public PassiveDevice(final String type) {
        super(type, CATEGORY);
    }
}