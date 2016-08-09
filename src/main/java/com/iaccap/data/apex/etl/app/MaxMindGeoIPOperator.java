package com.iaccap.data.apex.etl.app;

import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.common.util.BaseOperator;
import com.datatorrent.lib.logs.InformationExtractor;
import com.datatorrent.netlet.util.DTThrowable;
import com.esotericsoftware.kryo.NotNull;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.InvalidOpenTypeException;

/**
 * An implementation of InformationExtractor that extracts Geo information from an IP address using maxmind API .
 * <p>
 * Database path can be set to freshly downloaded file or use the one included with enricher
 * A re-map map can be specified to avoid name clashes with existing keys.
 * IMPORTANT: The user of this extractor needs to include the jars which contain these classes in DAGContext.LIBRARY_JARS
 * <p>
 * com.maxmind.geoip.LookupService.class
 *
 * @displayName Geo IP Extractor
 * @category Output
 * @tags extraction, geo
 * @since 0.9.4
 */
public class MaxMindGeoIPOperator extends BaseOperator {
    public transient DefaultOutputPort<Map<String, Object>> outputPort = new DefaultOutputPort<Map<String, Object>>();
    private static final Logger LOG = LoggerFactory.getLogger(MaxMindGeoIPOperator.class);
    private static final long serialVersionUID = 201604221817L;
    private transient LookupService reader;
    /**
     * The local path that contains the maxmind "legacy" GeoIP db
     */
    @NotNull
    private String lookupKey = "clientIp";

    private Map<String, String> mapReMap;

    private String databasePath;


    private final String[] knownAttributes = {
            "countryCode",
            "countryName",
            "region",
            "city",
            "postalCode",
            "latitude",
            "longitude",
            "area_code",
            "metro_code"};

    @Override
    public void setup(Context.OperatorContext context) {

        super.setup(context);
        try {
            // if db bath is configured, use it
            if (databasePath != null) {
                reader = new LookupService(databasePath, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
            } else {
                // else use the packaged in file last retrieved on 2016/09/06
                if (this.getClass().getClassLoader().getResource("META-INF/GeoLiteCity.dat") != null)
                    reader = new LookupService(new File(this.getClass().getClassLoader().getResource("META-INF/GeoLiteCity.dat").toURI()), LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
                else
                    throw new RuntimeException("GeoIP database not found in resources, nor configured explicitly.");
            }
        } catch (IOException | URISyntaxException | NullPointerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public transient DefaultInputPort<Map<String, Object>> inputPort = new DefaultInputPort<Map<String, Object>>() {
        @Override
        public void process(Map<String, Object> tuple) {
         //   try {
                if (tuple.containsKey(lookupKey)) {
                    String obj = (String) tuple.get(lookupKey);
                    if (obj != null) {
                        Location location = reader.getLocation(obj);
                        if (location != null) {
                            for (String attribute : knownAttributes) {
                                // TODO: Why can't I get this with reflection????
                                // String value = PropertyUtils.getProperty(location,attribute).toString();
                                String value="";
                                switch(attribute){
                                    case "countryCode" :
                                        value = location.countryCode;
                                        break;
                                    case "city":
                                        value = location.city;
                                        break;
                                    case "countryName":
                                        value = location.countryName;
                                        break;
                                    case "metro_code":
                                        value = String.valueOf(location.metro_code);
                                        break;
                                    case "region":
                                        value = location.region;
                                        break;
                                    case "latitude":
                                        value = String.valueOf(location.latitude);
                                        break;
                                    case "longitude":
                                        value = String.valueOf(location.longitude);
                                        break;
                                    case "postalCode":
                                        value=location.postalCode;
                                        break;

                                    default:
                                        break;
                                }
                                if (!value.isEmpty()) {
                                    if (mapReMap != null) {
                                        if ( mapReMap.containsKey(attribute) )
                                             tuple.put(mapReMap.get(attribute), value);
                                    } else {
                                        tuple.put(attribute, value);
                                    }
                                }

                            }
                        }
                    }
                }
      /**
            } catch ( NoSuchMethodException|InvocationTargetException e) {

            } catch (IllegalAccessException e) {
                DTThrowable.rethrow(e);
            }
       */
            outputPort.emit(tuple);
        }

    };

    @Override
    public void teardown() {
        reader.close();
        super.teardown();
    }

    @Override
    public void endWindow() {
        super.endWindow();
    }

    public String getLookupKey() {
        return lookupKey;
    }

    public void setLookupKey(String lookupKey) {
        this.lookupKey = lookupKey;
    }

    public Map<String, String> getMapReMap() {
        return mapReMap;
    }

    public void setMapReMap(Map<String, String> mapReMap) {
        this.mapReMap = mapReMap;
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public void setDatabasePath(String databasePath) {
        this.databasePath = databasePath;
    }

}
