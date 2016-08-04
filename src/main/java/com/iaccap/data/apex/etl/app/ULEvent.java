package com.iaccap.data.apex.etl.app;

import java.net.InetAddress;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * Created by dtadmin on 8/3/16.
 */
public class ULEvent {
    //always use com.fasterxml.uuid:java-uuid-generator comparator
    // applications should use robust generators and prefer v4 UUID random based generator over v1

    // eventID - unique logical event GUID
    private UUID eventId;                          // incoming
    // eventPartId - unique record ID corresponding to either server side, browser side, middle tier side or backend side oart of the event
    private UUID eventPartId;                      // incoming

    private String application;                    // incoming
    private String applicationBuildVersion;        // incoming
    private LocalDate applicationBuildDate;           // incoming

    private String eventName;                      // incoming

    private LocalDate eventDate;                   // derived
    private LocalDateTime eventTimestamp;          // incoming

    private String serverName;                     // incoming

    private String url;                            // incoming
    private String urlProtocol;                    // derived
    private String urlDomain;                      // derived
    private String urlPath;                        // derived
    private String urlPage;                        // derived
    private String urlQueryString;                 // derived

    private String httpMethod;                      // incoming
    private String httpCookies;                     // incoming
    private String httpPostData;                    // incoming
    private String httpContentType;                 // incoming
    private String httpResponseCode;                // incoming
    private String httpResponseTime;                // incoming
    private String httpResponseSize;                // incoming

    private InetAddress clientIp;                   // incoming
    // TODO: could be a 64-bit integer if storage in the database is more optimal, converting uuid to bigint may be a challange
    private UUID clientIpHash;                      // derived

    private String clientContinent;                 // enriched
    private String clientCountry;                   // enriched
    private String clientCity;                      // enriched
    private String clientState;                     // enriched
    private String clientCounty;                    // enriched
    private String clientNetwork;                   // enriched
    private String clientNetworkThroughput;         // enriched
    private String clientDMA;                       // enriched
    private String clientMSA;                       // enriched
    private String clientFIPS;                      // enriched
    private String clientTimeZone;                  // enriched
    private String clientZipCode;                   // enriched
    private String clientPMSA;                      // enriched
    private String clientAreaCode;                  // enriched
    private String clientLatitude;                  // enriched
    private String clientLongitude;                 // enriched
    private String clientCompany;                   // enriched

    private String clientUserAgent;                 // incoming
    private String clientBrowserType;               // derived
    private String clientBrowserVersion;            // derived
    private String clientBrowserLanguage;           // incoming
    private String clientPlatformType;              // derived
    private String clientPlatformVersion;           // derived

    private String clientScreenWidth;               // incoming
    private String clientScreenHeight;              // incoming
    private String clientColorDepth;                // incoming

    private Boolean backFillRequired;               // incoming
    private ULEventDetail eventDetail;              // pseudo container for application specific payload

    public ULEvent() {
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationBuildVersion() {
        return applicationBuildVersion;
    }

    public void setApplicationBuildVersion(String applicationBuildVersion) {
        this.applicationBuildVersion = applicationBuildVersion;
    }

    public LocalDate getApplicationBuildDate() {
        return applicationBuildDate;
    }

    /**
     * public void setApplicationBuildDate(LocalDate applicationBuildDate) {
     * this.applicationBuildDate = applicationBuildDate;
     * }
     */
    public void setApplicationBuildDate(String dateString) {
        this.applicationBuildDate = LocalDate.parse(dateString, ISODateTimeFormat.date());
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    /**
     * public void setEventDate(LocalDate eventDate) {
     * this.eventDate = eventDate;
     * }
     */
    public void setEventDate(String dateString) {
        this.eventDate = LocalDate.parse(dateString, ISODateTimeFormat.date());
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    /**
     * public void setEventTimestamp(LocalDateTime eventTimestamp) {
     * this.eventTimestamp = eventTimestamp;
     * }
     */
    public void setEventTimestamp(String timestampString) {
        this.eventTimestamp = LocalDateTime.parse(timestampString, ISODateTimeFormat.dateTime());
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUrlProtocol() {
        return urlProtocol;
    }

    public void setUrlProtocol(String urlProtocol) {
        this.urlProtocol = urlProtocol;
    }

    public String getUrlDomain() {
        return urlDomain;
    }

    public void setUrlDomain(String urlDomain) {
        this.urlDomain = urlDomain;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public String getUrlQueryString() {
        return urlQueryString;
    }

    public void setUrlQueryString(String urlQueryString) {
        this.urlQueryString = urlQueryString;
    }

    public String getHttpCookies() {
        return httpCookies;
    }

    public void setHttpCookies(String httpCookies) {
        this.httpCookies = httpCookies;
    }

    public String getHttpPostData() {
        return httpPostData;
    }

    public void setHttpPostData(String httpPostData) {
        this.httpPostData = httpPostData;
    }

    public String getHttpContentType() {
        return httpContentType;
    }

    public void setHttpContentType(String httpContentType) {
        this.httpContentType = httpContentType;
    }

    public String getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(String httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public String getHttpResponseTime() {
        return httpResponseTime;
    }

    public void setHttpResponseTime(String httpResponseTime) {
        this.httpResponseTime = httpResponseTime;
    }

    public String getHttpResponseSize() {
        return httpResponseSize;
    }

    public void setHttpResponseSize(String httpResponseSize) {
        this.httpResponseSize = httpResponseSize;
    }

    public InetAddress getClientIp() {
        return clientIp;
    }

    public void setClientIp(InetAddress clientIp) {
        this.clientIp = clientIp;
    }

    public UUID getClientIpHash() {
        return clientIpHash;
    }

    public void setClientIpHash(UUID clientIpHash) {
        this.clientIpHash = clientIpHash;
    }

    public String getClientContinent() {
        return clientContinent;
    }

    public void setClientContinent(String clientContinent) {
        this.clientContinent = clientContinent;
    }

    public String getClientCountry() {
        return clientCountry;
    }

    public void setClientCountry(String clientCountry) {
        this.clientCountry = clientCountry;
    }

    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }

    public String getClientState() {
        return clientState;
    }

    public void setClientState(String clientState) {
        this.clientState = clientState;
    }

    public String getClientCounty() {
        return clientCounty;
    }

    public void setClientCounty(String clientCounty) {
        this.clientCounty = clientCounty;
    }

    public String getClientNetwork() {
        return clientNetwork;
    }

    public void setClientNetwork(String clientNetwork) {
        this.clientNetwork = clientNetwork;
    }

    public String getClientNetworkThroughput() {
        return clientNetworkThroughput;
    }

    public void setClientNetworkThroughput(String clientNetworkThroughput) {
        this.clientNetworkThroughput = clientNetworkThroughput;
    }

    public String getClientDMA() {
        return clientDMA;
    }

    public void setClientDMA(String clientDMA) {
        this.clientDMA = clientDMA;
    }

    public String getClientMSA() {
        return clientMSA;
    }

    public void setClientMSA(String clientMSA) {
        this.clientMSA = clientMSA;
    }

    public String getClientFIPS() {
        return clientFIPS;
    }

    public void setClientFIPS(String clientFIPS) {
        this.clientFIPS = clientFIPS;
    }

    public String getClientTimeZone() {
        return clientTimeZone;
    }

    public void setClientTimeZone(String clientTimeZone) {
        this.clientTimeZone = clientTimeZone;
    }

    public String getClientZipCode() {
        return clientZipCode;
    }

    public void setClientZipCode(String clientZipCode) {
        this.clientZipCode = clientZipCode;
    }

    public String getClientPMSA() {
        return clientPMSA;
    }

    public void setClientPMSA(String clientPMSA) {
        this.clientPMSA = clientPMSA;
    }

    public String getClientAreaCode() {
        return clientAreaCode;
    }

    public void setClientAreaCode(String clientAreaCode) {
        this.clientAreaCode = clientAreaCode;
    }

    public String getClientLatitude() {
        return clientLatitude;
    }

    public void setClientLatitude(String clientLatitude) {
        this.clientLatitude = clientLatitude;
    }

    public String getClientLongitude() {
        return clientLongitude;
    }

    public void setClientLongitude(String clientLongitude) {
        this.clientLongitude = clientLongitude;
    }

    public String getClientCompany() {
        return clientCompany;
    }

    public void setClientCompany(String clientCompany) {
        this.clientCompany = clientCompany;
    }

    public String getClientBrowserType() {
        return clientBrowserType;
    }

    public void setClientBrowserType(String clientBrowserType) {
        this.clientBrowserType = clientBrowserType;
    }

    public String getClientBrowserVersion() {
        return clientBrowserVersion;
    }

    public void setClientBrowserVersion(String clientBrowserVersion) {
        this.clientBrowserVersion = clientBrowserVersion;
    }

    public String getClientBrowserLanguage() {
        return clientBrowserLanguage;
    }

    public void setClientBrowserLanguage(String clientBrowserLanguage) {
        this.clientBrowserLanguage = clientBrowserLanguage;
    }

    public String getClientPlatformType() {
        return clientPlatformType;
    }

    public void setClientPlatformType(String clientPlatformType) {
        this.clientPlatformType = clientPlatformType;
    }

    public String getClientPlatformVersion() {
        return clientPlatformVersion;
    }

    public void setClientPlatformVersion(String clientPlatformVersion) {
        this.clientPlatformVersion = clientPlatformVersion;
    }

    public String getClientScreenWidth() {
        return clientScreenWidth;
    }

    public void setClientScreenWidth(String clientScreenWidth) {
        this.clientScreenWidth = clientScreenWidth;
    }

    public String getClientScreenHeight() {
        return clientScreenHeight;
    }

    public void setClientScreenHeight(String clientScreenHeight) {
        this.clientScreenHeight = clientScreenHeight;
    }

    public String getClientColorDepth() {
        return clientColorDepth;
    }

    public void setClientColorDepth(String clientColorDepth) {
        this.clientColorDepth = clientColorDepth;
    }

    public ULEventDetail getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(ULEventDetail eventDetail) {
        this.eventDetail = eventDetail;
        eventDetail.setParentULEvent(this);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getClientUserAgent() {
        return clientUserAgent;
    }

    public void setClientUserAgent(String clientUserAgent) {
        this.clientUserAgent = clientUserAgent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getBackFillRequired() {
        return backFillRequired;
    }

    public void setBackFillRequired(Boolean backFillRequired) {
        this.backFillRequired = backFillRequired;
    }

    public UUID getEventPartId() {
        return eventPartId;
    }

    public void setEventPartId(UUID eventPartId) {
        this.eventPartId = eventPartId;
    }

    @Override
    public String toString() {
        return "ULEvent{" +
                "eventId=" + eventId +
                ", eventPartId=" + eventPartId +
                ", application='" + application + '\'' +
                ", applicationBuildVersion='" + applicationBuildVersion + '\'' +
                ", applicationBuildDate=" + applicationBuildDate +
                ", eventName='" + eventName + '\'' +
                ", eventDate=" + eventDate +
                ", eventTimestamp=" + eventTimestamp +
                ", serverName='" + serverName + '\'' +
                ", url='" + url + '\'' +
                ", urlProtocol='" + urlProtocol + '\'' +
                ", urlDomain='" + urlDomain + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", urlPage='" + urlPage + '\'' +
                ", urlQueryString='" + urlQueryString + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", httpCookies='" + httpCookies + '\'' +
                ", httpPostData='" + httpPostData + '\'' +
                ", httpContentType='" + httpContentType + '\'' +
                ", httpResponseCode='" + httpResponseCode + '\'' +
                ", httpResponseTime='" + httpResponseTime + '\'' +
                ", httpResponseSize='" + httpResponseSize + '\'' +
                ", clientIp=" + clientIp +
                ", clientIpHash=" + clientIpHash +
                ", clientContinent='" + clientContinent + '\'' +
                ", clientCountry='" + clientCountry + '\'' +
                ", clientCity='" + clientCity + '\'' +
                ", clientState='" + clientState + '\'' +
                ", clientCounty='" + clientCounty + '\'' +
                ", clientNetwork='" + clientNetwork + '\'' +
                ", clientNetworkThroughput='" + clientNetworkThroughput + '\'' +
                ", clientDMA='" + clientDMA + '\'' +
                ", clientMSA='" + clientMSA + '\'' +
                ", clientFIPS='" + clientFIPS + '\'' +
                ", clientTimeZone='" + clientTimeZone + '\'' +
                ", clientZipCode='" + clientZipCode + '\'' +
                ", clientPMSA='" + clientPMSA + '\'' +
                ", clientAreaCode='" + clientAreaCode + '\'' +
                ", clientLatitude='" + clientLatitude + '\'' +
                ", clientLongitude='" + clientLongitude + '\'' +
                ", clientCompany='" + clientCompany + '\'' +
                ", clientUserAgent='" + clientUserAgent + '\'' +
                ", clientBrowserType='" + clientBrowserType + '\'' +
                ", clientBrowserVersion='" + clientBrowserVersion + '\'' +
                ", clientBrowserLanguage='" + clientBrowserLanguage + '\'' +
                ", clientPlatformType='" + clientPlatformType + '\'' +
                ", clientPlatformVersion='" + clientPlatformVersion + '\'' +
                ", clientScreenWidth='" + clientScreenWidth + '\'' +
                ", clientScreenHeight='" + clientScreenHeight + '\'' +
                ", clientColorDepth='" + clientColorDepth + '\'' +
                ", backFillRequired=" + backFillRequired +
                ", eventDetail=" + eventDetail +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ULEvent ulEvent = (ULEvent) o;

        return new EqualsBuilder()
                .append(eventId, ulEvent.eventId)
                .append(eventPartId, ulEvent.eventPartId)
                .append(application, ulEvent.application)
                .append(applicationBuildVersion, ulEvent.applicationBuildVersion)
                .append(applicationBuildDate, ulEvent.applicationBuildDate)
                .append(eventName, ulEvent.eventName)
                .append(eventDate, ulEvent.eventDate)
                .append(eventTimestamp, ulEvent.eventTimestamp)
                .append(serverName, ulEvent.serverName)
                .append(url, ulEvent.url)
                .append(urlProtocol, ulEvent.urlProtocol)
                .append(urlDomain, ulEvent.urlDomain)
                .append(urlPath, ulEvent.urlPath)
                .append(urlPage, ulEvent.urlPage)
                .append(urlQueryString, ulEvent.urlQueryString)
                .append(httpMethod, ulEvent.httpMethod)
                .append(httpCookies, ulEvent.httpCookies)
                .append(httpPostData, ulEvent.httpPostData)
                .append(httpContentType, ulEvent.httpContentType)
                .append(httpResponseCode, ulEvent.httpResponseCode)
                .append(httpResponseTime, ulEvent.httpResponseTime)
                .append(httpResponseSize, ulEvent.httpResponseSize)
                .append(clientIp, ulEvent.clientIp)
                .append(clientIpHash, ulEvent.clientIpHash)
                .append(clientContinent, ulEvent.clientContinent)
                .append(clientCountry, ulEvent.clientCountry)
                .append(clientCity, ulEvent.clientCity)
                .append(clientState, ulEvent.clientState)
                .append(clientCounty, ulEvent.clientCounty)
                .append(clientNetwork, ulEvent.clientNetwork)
                .append(clientNetworkThroughput, ulEvent.clientNetworkThroughput)
                .append(clientDMA, ulEvent.clientDMA)
                .append(clientMSA, ulEvent.clientMSA)
                .append(clientFIPS, ulEvent.clientFIPS)
                .append(clientTimeZone, ulEvent.clientTimeZone)
                .append(clientZipCode, ulEvent.clientZipCode)
                .append(clientPMSA, ulEvent.clientPMSA)
                .append(clientAreaCode, ulEvent.clientAreaCode)
                .append(clientLatitude, ulEvent.clientLatitude)
                .append(clientLongitude, ulEvent.clientLongitude)
                .append(clientCompany, ulEvent.clientCompany)
                .append(clientUserAgent, ulEvent.clientUserAgent)
                .append(clientBrowserType, ulEvent.clientBrowserType)
                .append(clientBrowserVersion, ulEvent.clientBrowserVersion)
                .append(clientBrowserLanguage, ulEvent.clientBrowserLanguage)
                .append(clientPlatformType, ulEvent.clientPlatformType)
                .append(clientPlatformVersion, ulEvent.clientPlatformVersion)
                .append(clientScreenWidth, ulEvent.clientScreenWidth)
                .append(clientScreenHeight, ulEvent.clientScreenHeight)
                .append(clientColorDepth, ulEvent.clientColorDepth)
                .append(backFillRequired, ulEvent.backFillRequired)
                .append(eventDetail, ulEvent.eventDetail)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(eventId)
                .append(eventPartId)
                .append(application)
                .append(applicationBuildVersion)
                .append(applicationBuildDate)
                .append(eventName)
                .append(eventDate)
                .append(eventTimestamp)
                .append(serverName)
                .append(url)
                .append(urlProtocol)
                .append(urlDomain)
                .append(urlPath)
                .append(urlPage)
                .append(urlQueryString)
                .append(httpMethod)
                .append(httpCookies)
                .append(httpPostData)
                .append(httpContentType)
                .append(httpResponseCode)
                .append(httpResponseTime)
                .append(httpResponseSize)
                .append(clientIp)
                .append(clientIpHash)
                .append(clientContinent)
                .append(clientCountry)
                .append(clientCity)
                .append(clientState)
                .append(clientCounty)
                .append(clientNetwork)
                .append(clientNetworkThroughput)
                .append(clientDMA)
                .append(clientMSA)
                .append(clientFIPS)
                .append(clientTimeZone)
                .append(clientZipCode)
                .append(clientPMSA)
                .append(clientAreaCode)
                .append(clientLatitude)
                .append(clientLongitude)
                .append(clientCompany)
                .append(clientUserAgent)
                .append(clientBrowserType)
                .append(clientBrowserVersion)
                .append(clientBrowserLanguage)
                .append(clientPlatformType)
                .append(clientPlatformVersion)
                .append(clientScreenWidth)
                .append(clientScreenHeight)
                .append(clientColorDepth)
                .append(backFillRequired)
                .append(eventDetail)
                .toHashCode();
    }
}
