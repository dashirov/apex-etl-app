package com.iaccap.data.apex.etl.app;

import com.esotericsoftware.jsonbeans.Json;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.map.HashedMap;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by dtadmin on 8/3/16.
 */
public class ULEventMap {
    //always use com.fasterxml.uuid:java-uuid-generator comparator
    // applications should use robust generators and prefer v4 UUID random based generator over v1

    private Map<String,Object> event;

    public enum CommonSchemaAttr{
        EVENT_ID                    ("EventId",UUID.class),
        EVENT_PART_ID               ("eventPartId",String.class),
        APPLICATION                 ("application",String.class),
        APPLICATION_BUILD_VERSION   ("applicationBuildVersion",String.class),
        APPLICATION_BUILD_DATE      ("applicationBuildDate",LocalDate.class),
        EVENT_NAME                  ("eventName",String.class),
        EVENT_TIMESTAMP             ("eventTimestamp",LocalDateTime.class),
        SERVER_NAME                 ("serverName",String.class),
        URL                         ("url", java.net.URL.class),
        URL_PROTOCOL                ("urlProtocol",String.class),
        URL_DOMAIN                  ("urlDomain",String.class),
        URL_PATH                    ("urlPath",String.class),
        URL_PAGE                    ("urlPage",String.class),
        URL_QUERY_STRING            ("urlQueryString",String.class),
        HTTP_METHOD                 ("httpMethod",String.class),
        HTTP_COOKIES                ("httpCookies",String.class),
        HTTP_POSTDATA               ("httpPostData",String.class),
        HTTP_CONTENT_TYPE           ("httpContentType",String.class),
        HTTP_RESPONSE_CODE          ("httpResponseCode",Integer.class),
        HTTP_RESPONSE_TIME          ("httpResponseTime",Integer.class),
        HTTP_RESPONSE_SIZE          ("httpResponseSize",Integer.class),
        CLIENT_IP                   ("clientIp",InetAddress.class),
        CLIENT_IPHASH               ("clientIpHash",UUID.class),
        CLIENT_CONTINENT            ("clientContinent",String.class),
        CLIENT_COUNTRY              ("clientCountry",String.class),
        CLIENT_CITY                 ("clientCity",String.class),
        CLIENT_STATE                ("clientState",String.class),
        CLIENT_COUNTY               ("clientCounty",String.class),
        CLIENT_NETWORK              ("clientNetwork",String.class),
        CLIENT_NETWORK_THROUGHPUT   ("clientNetworkThroughput",String.class),
        CLIENT_DMA                  ("clientDMA",String.class),
        CLIENT_MSA                  ("clientMSA",String.class),
        CLIENT_FIPS                 ("clientFIPS",String.class),
        CLIENT_TIMEZONE             ("clientTimeZone",String.class),
        CLIENT_ZIPCODES             ("clientZipCodes",String.class),
        CLIENT_PMSA                 ("clientPMSA",String.class),
        CLIENT_AREACODE             ("clientAreaCode",String.class),
        CLIENT_LATITUDE             ("clientLatitude",String.class),
        CLIENT_LONGITUDE            ("clientLongitude",String.class),
        CLIENT_COMPANY              ("clientCompany",String.class),
        CLIENT_USER_AGENT           ("clientUserAgent",String.class),
        CLIENT_BROWSER_TYPE         ("clientBrowserType",String.class),
        CLIENT_BROWSER_VERSION      ("clientBrowserVersion",String.class),
        CLIENT_BROWSER_LANGUAGE     ("clientBrowserLanguage",String.class),
        CLIENT_PLATFORM_TYPE        ("clientPlatformType",String.class),
        CLIENT_PLATFORM_VERSION     ("clientPlatformVersion",String.class),
        CLIENT_SCREEN_WIDTH         ("clientScreenWidth",Integer.class),
        CLIENT_SCREEN_HEIGHT        ("clientScreenHeight",Integer.class),
        CLIENT_COLOR_DEPTH          ("clientColorDepth",Integer.class),
        BACKFILL_REQUIRED           ("backFillRequired",Boolean.class),
        EVENT_DETAIL                ("eventDetail",Map.class);
        private final String key;
        private final Class clazz;

        CommonSchemaAttr(String key, java.lang.Class clazz) {
            this.key=key;
            this.clazz=clazz;
        }
        private String key(){ return key;}
        private Class clazz(){return clazz;}

    }


    public ULEventMap() {
        this.event = new HashedMap();
    }
    public ULEventMap(Map<String,Object> map){
        this.event = new HashedMap(map);
    }



    @JsonProperty
    public UUID getEventId() {
        if ( event.containsKey(CommonSchemaAttr.EVENT_ID.key()) ) {
            return UUID.fromString((String) event.get(CommonSchemaAttr.EVENT_ID.key()));
        }
        return null;
    }
    @JsonProperty
    public void setEventId(String eventId) {
        this.event.put(CommonSchemaAttr.EVENT_ID.key(),eventId);
    }

    @JsonProperty
    public void setEventPartId(String eventPartId){
        this.event.put(CommonSchemaAttr.EVENT_PART_ID.key(), eventPartId);
    }
    @JsonProperty
    public  UUID getEventPartId(){
        return UUID.fromString((String) event.get(CommonSchemaAttr.EVENT_PART_ID.key()));
    }

    @JsonProperty
    public String getApplication() {
        return (String) event.get(CommonSchemaAttr.APPLICATION.key());
    }
    @JsonProperty
    public void setApplication(String application) {
        this.event.put(CommonSchemaAttr.APPLICATION.key(), application);
    }
    @JsonProperty
    public String getApplicationBuildVersion() {
        return (String) event.get(CommonSchemaAttr.APPLICATION_BUILD_VERSION.key());
    }
    @JsonProperty
    public void setApplicationBuildVersion(String applicationBuildVersion) {
        this.event.put(CommonSchemaAttr.APPLICATION_BUILD_VERSION.key(), applicationBuildVersion);
    }
    @JsonProperty
    public LocalDate getApplicationBuildDate() {
        return LocalDate.parse((String) this.event.get(CommonSchemaAttr.APPLICATION_BUILD_DATE.key()),ISODateTimeFormat.date());
    }
    @JsonProperty
    public void setApplicationBuildDate(String dateString) {
        this.event.put(CommonSchemaAttr.APPLICATION_BUILD_DATE.key(), dateString);
    }
    @JsonProperty
    public Map<String, Object> getEventDetail() {
        return (Map<String,Object>) this.event.get(CommonSchemaAttr.EVENT_DETAIL.key());
    }
    @JsonProperty
    public  void setEventDetail(Map<String, Object> payload) {
        this.event.put(CommonSchemaAttr.EVENT_DETAIL.key(), payload);
    }

    @JsonProperty
    public void setEventName(String eventName){
        this.event.put(CommonSchemaAttr.EVENT_NAME.key(), eventName);
    }
    @JsonProperty
    public  String getEventName(){
        return (String) event.get(CommonSchemaAttr.EVENT_NAME.key());
    }

    @JsonProperty
    public void setEventTimestamp(String eventTimestamp){
        this.event.put(CommonSchemaAttr.EVENT_TIMESTAMP.key(), eventTimestamp);
    }
    @JsonProperty
    public  LocalDateTime getEventTimestamp(){
        return LocalDateTime.parse( ((String) event.get(CommonSchemaAttr.EVENT_TIMESTAMP.key())), ISODateTimeFormat.dateTime());
    }

    @JsonProperty
    public void setServerName(String serverName){
        this.event.put(CommonSchemaAttr.SERVER_NAME.key(), serverName);
    }
    @JsonProperty
    public  String getServerName(){
        return (String) event.get(CommonSchemaAttr.SERVER_NAME.key());
    }

    @JsonProperty
    public void setURL(String url){
        this.event.put(CommonSchemaAttr.URL.key(), url);
    }
    @JsonProperty
    public URL getURL(){
        try {
            return new URL((String) event.get(CommonSchemaAttr.URL.key()));
        } catch (MalformedURLException e){
            return null;
        }
    }

    public String getURLString(){
        return (String) event.get(CommonSchemaAttr.URL.key());
    }

    @JsonProperty
    public void setUrlProtocol(String urlProtocol){
        this.event.put(CommonSchemaAttr.URL_PROTOCOL.key(), urlProtocol);
    }
    @JsonProperty
    public  String getUrlProtocol(){
        return (String) event.get(CommonSchemaAttr.URL_PROTOCOL.key());
    }

    @JsonProperty
    public void setUrlDomain(String urlDomain){
        this.event.put(CommonSchemaAttr.URL_DOMAIN.key(), urlDomain);
    }
    @JsonProperty
    public  String getUrlDomain(){
        return (String) event.get(CommonSchemaAttr.URL_DOMAIN.key());
    }

    @JsonProperty
    public void setUrlPath(String urlPath){
        this.event.put(CommonSchemaAttr.URL_PATH.key(), urlPath);
    }
    @JsonProperty
    public  String getUrlPath(){
        return (String) event.get(CommonSchemaAttr.URL_PATH.key());
    }

    @JsonProperty
    public void setUrlPage(String urlPage){
        this.event.put(CommonSchemaAttr.URL_PAGE.key(), urlPage);
    }
    @JsonProperty
    public  String getUrlPage(){
        return (String) event.get(CommonSchemaAttr.URL_PAGE.key());
    }

    @JsonProperty
    public void setUrlQueryString(String urlQueryString){
        this.event.put(CommonSchemaAttr.URL_QUERY_STRING.key(), urlQueryString);
    }
    @JsonProperty
    public  String getUrlQueryString(){
        return (String) event.get(CommonSchemaAttr.URL_QUERY_STRING.key());
    }

    @JsonProperty
    public void setHttpMethod(String httpMethod){
        this.event.put(CommonSchemaAttr.HTTP_METHOD.key(), httpMethod);
    }
    @JsonProperty
    public  String getHttpMethod(){
        return (String) event.get(CommonSchemaAttr.HTTP_METHOD.key());
    }

    @JsonProperty
    public void setHttpCookies(String httpCookies){
        this.event.put(CommonSchemaAttr.HTTP_COOKIES.key(), httpCookies);
    }
    @JsonProperty
    public  String getHttpCookies(){
        return (String) event.get(CommonSchemaAttr.HTTP_COOKIES.key());
    }

    @JsonProperty
    public void setHttpPostData(String httpPostData){
        this.event.put(CommonSchemaAttr.HTTP_POSTDATA.key(), httpPostData);
    }
    @JsonProperty
    public  String getHttpPostData(){
        return (String) event.get(CommonSchemaAttr.HTTP_POSTDATA.key());
    }

    @JsonProperty
    public void setHttpContentType(String httpContentType){
        this.event.put(CommonSchemaAttr.HTTP_CONTENT_TYPE.key(), httpContentType);
    }
    @JsonProperty
    public  String getHttpContentType(){
        return (String) event.get(CommonSchemaAttr.HTTP_CONTENT_TYPE.key());
    }

    @JsonProperty
    public void setHttpResponseCode(Integer httpResponseCode){
        this.event.put(CommonSchemaAttr.HTTP_RESPONSE_CODE.key(), httpResponseCode);
    }
    @JsonProperty
    public  Integer getHttpResponseCode(){
        return (Integer) event.get(CommonSchemaAttr.HTTP_RESPONSE_CODE.key());
    }

    @JsonProperty
    public void setHttpResponseTime(Integer httpResponseTime){
        this.event.put(CommonSchemaAttr.HTTP_RESPONSE_TIME.key(), httpResponseTime);
    }
    @JsonProperty
    public  Integer getHttpResponseTime(){
        return (Integer) event.get(CommonSchemaAttr.HTTP_RESPONSE_TIME.key());
    }

    @JsonProperty
    public void setHttpResponseSize(Integer httpResponseSize){
        this.event.put(CommonSchemaAttr.HTTP_RESPONSE_SIZE.key(), httpResponseSize);
    }
    @JsonProperty
    public  Integer getHttpResponseSize(){
        return (Integer) event.get(CommonSchemaAttr.HTTP_RESPONSE_SIZE.key());
    }

    @JsonProperty
    public void setClientIp(String clientIp){
        this.event.put(CommonSchemaAttr.CLIENT_IP.key(), clientIp);
    }
    @JsonProperty
    public  InetAddress getClientIp(){
        try {
        return (InetAddress.getByName( (String) event.get(CommonSchemaAttr.CLIENT_IP.key())));
        } catch (UnknownHostException e){
            return null;
        }
    }
public String getClientIpString(){
    return (String) event.get(CommonSchemaAttr.CLIENT_IP.key());
}
    @JsonProperty
    public void setClientIpHash(String clientIpHash){
        this.event.put(CommonSchemaAttr.CLIENT_IPHASH.key(), clientIpHash);
    }
    @JsonProperty
    public  UUID getClientIpHash(){
        return UUID.fromString(( String) event.get(CommonSchemaAttr.CLIENT_IPHASH.key()));
    }

    @JsonProperty
    public void setClientContinent(String clientContinent){
        this.event.put(CommonSchemaAttr.CLIENT_CONTINENT.key(), clientContinent);
    }
    @JsonProperty
    public  String getClientContinent(){
        return (String) event.get(CommonSchemaAttr.CLIENT_CONTINENT.key());
    }

    @JsonProperty
    public void setClientCountry(String clientCountry){
        this.event.put(CommonSchemaAttr.CLIENT_COUNTRY.key(), clientCountry);
    }
    @JsonProperty
    public  String getClientCountry(){
        return (String) event.get(CommonSchemaAttr.CLIENT_COUNTRY.key());
    }

    @JsonProperty
    public void setClientCity(String clientCity){
        this.event.put(CommonSchemaAttr.CLIENT_CITY.key(), clientCity);
    }
    @JsonProperty
    public  String getClientCity(){
        return (String) event.get(CommonSchemaAttr.CLIENT_CITY.key());
    }

    @JsonProperty
    public void setClientState(String clientState){
        this.event.put(CommonSchemaAttr.CLIENT_STATE.key(), clientState);
    }
    @JsonProperty
    public  String getClientState(){
        return (String) event.get(CommonSchemaAttr.CLIENT_STATE.key());
    }

    @JsonProperty
    public void setClientCounty(String clientCounty){
        this.event.put(CommonSchemaAttr.CLIENT_COUNTY.key(), clientCounty);
    }
    @JsonProperty
    public  String getClientCounty(){
        return (String) event.get(CommonSchemaAttr.CLIENT_COUNTY.key());
    }

    @JsonProperty
    public void setClientNetwork(String clientNetwork){
        this.event.put(CommonSchemaAttr.CLIENT_NETWORK.key(), clientNetwork);
    }
    @JsonProperty
    public  String getClientNetwork(){
        return (String) event.get(CommonSchemaAttr.CLIENT_NETWORK.key());
    }

    @JsonProperty
    public void setClientNetworkThroughput(String clientNetworkThroughput){
        this.event.put(CommonSchemaAttr.CLIENT_NETWORK_THROUGHPUT.key(), clientNetworkThroughput);
    }
    @JsonProperty
    public  String getClientNetworkThroughput(){
        return (String) event.get(CommonSchemaAttr.CLIENT_NETWORK_THROUGHPUT.key());
    }

    @JsonProperty
    public void setClientDMA(String clientDMA){
        this.event.put(CommonSchemaAttr.CLIENT_DMA.key(), clientDMA);
    }
    @JsonProperty
    public  String getClientDMA(){
        return (String) event.get(CommonSchemaAttr.CLIENT_DMA.key());
    }

    @JsonProperty
    public void setClientMSA(String clientMSA){
        this.event.put(CommonSchemaAttr.CLIENT_MSA.key(), clientMSA);
    }
    @JsonProperty
    public  String getClientMSA(){
        return (String) event.get(CommonSchemaAttr.CLIENT_MSA.key());
    }

    @JsonProperty
    public void setClientFIPS(String clientFIPS){
        this.event.put(CommonSchemaAttr.CLIENT_FIPS.key(), clientFIPS);
    }
    @JsonProperty
    public  String getClientFIPS(){
        return (String) event.get(CommonSchemaAttr.CLIENT_FIPS.key());
    }

    @JsonProperty
    public void setClientTimeZone(String clientTimeZone){
        this.event.put(CommonSchemaAttr.CLIENT_TIMEZONE.key(), clientTimeZone);
    }
    @JsonProperty
    public  String getClientTimeZone(){
        return (String) event.get(CommonSchemaAttr.CLIENT_TIMEZONE.key());
    }

    @JsonProperty
    public void setClientZipCodes(String clientZipCodes){
        this.event.put(CommonSchemaAttr.CLIENT_ZIPCODES.key(), clientZipCodes);
    }
    @JsonProperty
    public  String getClientZipCodes(){
        return (String) event.get(CommonSchemaAttr.CLIENT_ZIPCODES.key());
    }

    @JsonProperty
    public void setClientPMSA(String clientPMSA){
        this.event.put(CommonSchemaAttr.CLIENT_PMSA.key(), clientPMSA);
    }
    @JsonProperty
    public  String getClientPMSA(){
        return (String) event.get(CommonSchemaAttr.CLIENT_PMSA.key());
    }

    @JsonProperty
    public void setClientAreaCode(String clientAreaCode){
        this.event.put(CommonSchemaAttr.CLIENT_AREACODE.key(), clientAreaCode);
    }
    @JsonProperty
    public  String getClientAreaCode(){
        return (String) event.get(CommonSchemaAttr.CLIENT_AREACODE.key());
    }

    @JsonProperty
    public void setClientLatitude(String clientLatitude){
        this.event.put(CommonSchemaAttr.CLIENT_LATITUDE.key(), clientLatitude);
    }
    @JsonProperty
    public  String getClientLatitude(){
        return (String) event.get(CommonSchemaAttr.CLIENT_LATITUDE.key());
    }

    @JsonProperty
    public void setClientLongitude(String clientLongitude){
        this.event.put(CommonSchemaAttr.CLIENT_LONGITUDE.key(), clientLongitude);
    }
    @JsonProperty
    public  String getClientLongitude(){
        return (String) event.get(CommonSchemaAttr.CLIENT_LONGITUDE.key());
    }

    @JsonProperty
    public void setClientCompany(String clientCompany){
        this.event.put(CommonSchemaAttr.CLIENT_COMPANY.key(), clientCompany);
    }
    @JsonProperty
    public  String getClientCompany(){
        return (String) event.get(CommonSchemaAttr.CLIENT_COMPANY.key());
    }

    @JsonProperty
    public void setClientUserAgent(String clientUserAgent){
        this.event.put(CommonSchemaAttr.CLIENT_USER_AGENT.key(), clientUserAgent);
    }
    @JsonProperty
    public  String getClientUserAgent(){
        return (String) event.get(CommonSchemaAttr.CLIENT_USER_AGENT.key());
    }

    @JsonProperty
    public void setClientBrowserType(String clientBrowserType){
        this.event.put(CommonSchemaAttr.CLIENT_BROWSER_TYPE.key(), clientBrowserType);
    }
    @JsonProperty
    public  String getClientBrowserType(){
        return (String) event.get(CommonSchemaAttr.CLIENT_BROWSER_TYPE.key());
    }

    @JsonProperty
    public void setClientBrowserVersion(String clientBrowserVersion){
        this.event.put(CommonSchemaAttr.CLIENT_BROWSER_VERSION.key(), clientBrowserVersion);
    }
    @JsonProperty
    public  String getClientBrowserVersion(){
        return (String) event.get(CommonSchemaAttr.CLIENT_BROWSER_VERSION.key());
    }

    @JsonProperty
    public void setClientBrowserLanguage(String clientBrowserLanguage){
        this.event.put(CommonSchemaAttr.CLIENT_BROWSER_LANGUAGE.key(), clientBrowserLanguage);
    }
    @JsonProperty
    public  String getClientBrowserLanguage(){
        return (String) event.get(CommonSchemaAttr.CLIENT_BROWSER_LANGUAGE.key());
    }

    @JsonProperty
    public void setClientPlatformType(String clientPlatformType){
        this.event.put(CommonSchemaAttr.CLIENT_PLATFORM_TYPE.key(), clientPlatformType);
    }
    @JsonProperty
    public  String getClientPlatformType(){
        return (String) event.get(CommonSchemaAttr.CLIENT_PLATFORM_TYPE.key());
    }

    @JsonProperty
    public void setClientPlatformVersion(String clientPlatformVersion){
        this.event.put(CommonSchemaAttr.CLIENT_PLATFORM_VERSION.key(), clientPlatformVersion);
    }
    @JsonProperty
    public  String getClientPlatformVersion(){
        return (String) event.get(CommonSchemaAttr.CLIENT_PLATFORM_VERSION.key());
    }

    @JsonProperty
    public void setClientScreenWidth(String clientScreenWidth){
        this.event.put(CommonSchemaAttr.CLIENT_SCREEN_WIDTH.key(), clientScreenWidth);
    }
    @JsonProperty
    public  Integer getClientScreenWidth(){
        return (Integer) event.get(CommonSchemaAttr.CLIENT_SCREEN_WIDTH.key());
    }

    @JsonProperty
    public void setClientScreenHeight(String clientScreenHeight){
        this.event.put(CommonSchemaAttr.CLIENT_SCREEN_HEIGHT.key(), clientScreenHeight);
    }
    @JsonProperty
    public  Integer getClientScreenHeight(){
        return (Integer) event.get(CommonSchemaAttr.CLIENT_SCREEN_HEIGHT.key());
    }

    @JsonProperty
    public void setClientColorDepth(String clientColorDepth){
        this.event.put(CommonSchemaAttr.CLIENT_COLOR_DEPTH.key(), clientColorDepth);
    }
    @JsonProperty
    public  Integer getClientColorDepth(){
        return (Integer) event.get(CommonSchemaAttr.CLIENT_COLOR_DEPTH.key());
    }

    @JsonProperty
    public void setBackFillRequired(String backFillRequired){
        this.event.put(CommonSchemaAttr.BACKFILL_REQUIRED.key(), backFillRequired);
    }
    @JsonProperty
    public  Boolean getBackFillRequired(){
        return (Boolean) event.get(CommonSchemaAttr.BACKFILL_REQUIRED.key());
    }

    @Override
    public String toString() {
        return "ULEventMap{" +
                "event=" + event +
                '}';
    }
}
