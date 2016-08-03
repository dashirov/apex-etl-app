package com.iaccap.data.apex.etl.app;

import java.net.InetAddress;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private String applicationBuildDate;           // incoming

    private String eventType;                      // incoming

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
    private String clientBrowserLanguage;           // derived
    private String clientPlatformType;              // derived
    private String clientPlatformVersion;           // derived

    private String clientScreenWidth;               // incoming
    private String clientScreenHeight;              // incoming
    private String clientColorDepth;                // incoming

    private Boolean backFillRequired;               // incoming
    private ULEventDetail eventDetail;              // pseudo container for application specific payload


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

    public String getApplicationBuildDate() {
        return applicationBuildDate;
    }

    public void setApplicationBuildDate(String applicationBuildDate) {
        this.applicationBuildDate = applicationBuildDate;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalDateTime getEventTimestamp() {
        return eventTimestamp;
    }

    public void setEventTimestamp(LocalDateTime eventTimestamp) {
        this.eventTimestamp = eventTimestamp;
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
}
