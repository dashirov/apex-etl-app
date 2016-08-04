package com.iaccap.data.apex.etl.app;
import java.io.Serializable;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.datatorrent.lib.codec.KryoJdkContainer;

import org.apache.commons.lang3.builder.EqualsBuilder;
/**
 * Created by dtadmin on 8/4/16.
 */



@DefaultSerializer(JavaSerializer.class)
public class ULEventEntry extends KryoJdkContainer<ULEvent> implements Serializable
{
    /**
     * Empty constructor.
     */
    public ULEventEntry()
    {
    }

    /**
     * Create a ULEventEntry using a Rome SyndEntry object.
     *
     * @param ulEvent The ULEvent object
     */
    public ULEventEntry(ULEvent ulEvent)
    {
        super(ulEvent);
    }

    /**
     * Set the Rome SyndEntry object.
     *
     * @param ulEvent The SyndEntry object
     */
    public void setSyndEntry(ULEvent ulEvent)
    {
        setComponent(ulEvent);
    }

    /**
     * Get the Rome SyndEntry object.
     *
     * @return The ULEvent object
     */
    public ULEvent getULEvent()
    {
        return getComponent();
    }

    /**
     * Override equals to tell if the given object is equal to this ULEvent object.
     * Compares all members of pojo of the underlying ULEvent of both objects to determine equality.
     *
     * @param o The given object
     * @return Whether the given object is equal to this object or not
     *
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;
        if (this.getComponent() == null || ((ULEventEntry) o).getComponent()==null) return false;
        return this.getComponent().equals(((ULEventEntry) o).getComponent());
    }

    @Override
    public String toString() {
        return "ULEventEntry{}" + ( (getComponent() != null) ? getComponent().toString() : "null" );
    }

}