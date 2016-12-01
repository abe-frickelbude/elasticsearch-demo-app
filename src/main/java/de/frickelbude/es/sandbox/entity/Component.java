package de.frickelbude.es.sandbox.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for all electronic component representations, provides some common attributes, as well as an common
 * interface for the attributes essential to the indexer.
 * 
 * Most of the methods in this class are declared final, since overriding them may cause potential inconsistencies in
 * the essential attributes of a Component.
 * 
 * @author Ibragim Kuliev
 *
 */
@JsonPropertyOrder({
    "id", "type", "category", "description", "manufacturer", "createDate"
})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
    @Type(name = Resistor.TYPE, value = Resistor.class),
    @Type(name = Capacitor.TYPE, value = Capacitor.class),
    @Type(name = Inductor.TYPE, value = Inductor.class),
    @Type(name = Diode.TYPE, value = Diode.class),
    @Type(name = BipolarTransistor.TYPE, value = BipolarTransistor.class),
    @Type(name = MosfetTransistor.TYPE, value = MosfetTransistor.class)
})
public abstract class Component {

    private Long id;
    private final String type;
    private final String category;
    private String description;
    private Manufacturer manufacturer;
    private DateTime createDate;

    public Component(final String type, final String category) {
        this.type = type;
        this.category = category;
        createDate = DateTime.now(DateTimeZone.UTC);
    }

    public final Long getId() {
        return id;
    }

    public final void setId(final Long id) {
        this.id = id;
    }

    /**
     * Component type, e.g. "bjt". This is used to determine the type attribute for the JSON data (for automatic
     * serialization/deserialization of any type in the {@linkplain Component} class hierarchy), as well as for the
     * option ES "type" field that the will be used to index the document.
     */
    public final String getType() {
        return this.type;
    }

    /**
     * Component category, e.g. "resistor". To be implemented by every subclass. This is used to determine the ES index
     * core that the will be to index to document.
     */
    public final String getCategory() {
        return this.category;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final Manufacturer getManufacturer() {
        return manufacturer;
    }

    public final void setManufacturer(final Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public final DateTime getCreateDate() {
        return createDate;
    }

    public final void setCreateDate(final DateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append("\n\ttype: ");
        builder.append(getType());
        builder.append("\n\tcategory: ");
        builder.append(getCategory());
        builder.append("\n\t");
        builder.append(ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE));
        builder.append("\n}");
        return builder.toString();
    }

}
