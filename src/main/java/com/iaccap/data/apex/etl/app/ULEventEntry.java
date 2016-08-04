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
     * Create a RomeFeedEntry using a Rome SyndEntry object.
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
        ULEventEntry rfe = (ULEventEntry) o;
        ULEvent rf = rfe.getComponent();
        ULEvent ulEvent = getComponent();

        return new EqualsBuilder()
                .append(rf.getEventId(), ulEvent.getEventId())
                .append(rf.getEventPartId(), ulEvent.getEventPartId())
                .append(rf.getApplication(), ulEvent.getApplication())
                .append(rf.getApplicationBuildVersion(), ulEvent.getApplicationBuildVersion())
                .append(rf.getApplicationBuildDate(), ulEvent.getApplicationBuildDate())
                .append(rf.getEventName(), ulEvent.getEventName())
                .append(rf.getEventDate(), ulEvent.getEventDate())
                .append(rf.getEventTimestamp(), ulEvent.getEventTimestamp())
                .append(rf.getServerName(), ulEvent.getServerName())
                .append(rf.getUrl(), ulEvent.getUrl())
                .append(rf.getUrlProtocol(), ulEvent.getUrlProtocol())
                .append(rf.getUrlDomain(), ulEvent.getUrlDomain())
                .append(rf.getUrlPath(), ulEvent.getUrlPath())
                .append(rf.getUrlPage(), ulEvent.getUrlPage())
                .append(rf.getUrlQueryString(), ulEvent.getUrlQueryString())
                .append(rf.getHttpMethod(), ulEvent.getHttpMethod())
                .append(rf.getHttpCookies(), ulEvent.getHttpCookies())
                .append(rf.getHttpPostData(), ulEvent.getHttpPostData())
                .append(rf.getHttpContentType(), ulEvent.getHttpContentType())
                .append(rf.getHttpResponseCode(), ulEvent.getHttpResponseCode())
                .append(rf.getHttpResponseTime(), ulEvent.getHttpResponseTime())
                .append(rf.getHttpResponseSize(), ulEvent.getHttpResponseSize())
                .append(rf.getClientIp(), ulEvent.getClientIp())
                .append(rf.getClientIpHash(), ulEvent.getClientIpHash())
                .append(rf.getClientContinent(), ulEvent.getClientContinent())
                .append(rf.getClientCountry(), ulEvent.getClientCountry())
                .append(rf.getClientCity(), ulEvent.getClientCity())
                .append(rf.getClientState(), ulEvent.getClientState())
                .append(rf.getClientCounty(), ulEvent.getClientCounty())
                .append(rf.getClientNetwork(), ulEvent.getClientNetwork())
                .append(rf.getClientNetworkThroughput(), ulEvent.getClientNetworkThroughput())
                .append(rf.getClientDMA(), ulEvent.getClientDMA())
                .append(rf.getClientMSA(), ulEvent.getClientMSA())
                .append(rf.getClientFIPS(), ulEvent.getClientFIPS())
                .append(rf.getClientTimeZone(), ulEvent.getClientTimeZone())
                .append(rf.getClientZipCode(), ulEvent.getClientZipCode())
                .append(rf.getClientPMSA(), ulEvent.getClientPMSA())
                .append(rf.getClientAreaCode(), ulEvent.getClientAreaCode())
                .append(rf.getClientLatitude(), ulEvent.getClientLatitude())
                .append(rf.getClientLongitude(), ulEvent.getClientLongitude())
                .append(rf.getClientCompany(), ulEvent.getClientCompany())
                .append(rf.getClientUserAgent(), ulEvent.getClientUserAgent())
                .append(rf.getClientBrowserType(), ulEvent.getClientBrowserType())
                .append(rf.getClientBrowserVersion(), ulEvent.getClientBrowserVersion())
                .append(rf.getClientBrowserLanguage(), ulEvent.getClientBrowserLanguage())
                .append(rf.getClientPlatformType(), ulEvent.getClientPlatformType())
                .append(rf.getClientPlatformVersion(), ulEvent.getClientPlatformVersion())
                .append(rf.getClientScreenWidth(), ulEvent.getClientScreenWidth())
                .append(rf.getClientScreenHeight(), ulEvent.getClientScreenHeight())
                .append(rf.getClientColorDepth(), ulEvent.getClientColorDepth())
                .append(rf.getBackFillRequired(), ulEvent.getBackFillRequired())
                .append(rf.getEventDetail(), ulEvent.getEventDetail())
                .isEquals();
    }

    @Override
    public String toString() {
        return "ULEventEntry{}" + ( (getComponent() != null) ? getComponent().toString() : "null" );
    }
}