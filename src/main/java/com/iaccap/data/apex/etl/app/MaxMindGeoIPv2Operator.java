package com.iaccap.data.apex.etl.app;

import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.common.util.BaseOperator;
import com.datatorrent.netlet.util.DTThrowable;

import com.esotericsoftware.kryo.NotNull;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.util.Map;

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
public class MaxMindGeoIPv2Operator extends BaseOperator {
    public transient DefaultOutputPort<Map<String, Object>> outputPort = new DefaultOutputPort<Map<String, Object>>();
    private static final Logger LOG = LoggerFactory.getLogger(MaxMindGeoIPv2Operator.class);
    private static final long serialVersionUID = 201604221817L;
    private transient DatabaseReader reader;
    /**
     * The local path that contains the maxmind  GeoIP v2  city db
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
                reader = new DatabaseReader.Builder(new File(databasePath)).build();
            } else {
                // else use the packaged in file last retrieved on 2016/09/06
                if (this.getClass().getClassLoader().getResource("META-INF/GeoIP.dat") != null) {
                    reader = new DatabaseReader.Builder(new File(this.getClass().getClassLoader().getResource("META-INF/GGeoIP2-City.mmdb").toURI())).build();
                } else {
                    throw new RuntimeException("GeoIP database not found in resources, nor configured explicitly.");
                }
            }
        } catch (IOException | URISyntaxException | NullPointerException ex) {
            throw new RuntimeException(ex);
        }
    }

    public transient DefaultInputPort<Map<String, Object>> inputPort = new DefaultInputPort<Map<String, Object>>() {
        @Override
        public void process(Map<String, Object> tuple) {
            if (tuple.containsKey(lookupKey)) {
                String obj = (String) tuple.get(lookupKey);
                if (obj != null) {
                    try {
                        CityResponse location = reader.city(InetAddress.getByName(obj));

                        if (location != null) {
                            for (String attribute : knownAttributes) {
                                try {
                                    if (mapReMap != null && mapReMap.containsKey(attribute)) {
                                        tuple.put(mapReMap.get(attribute), location.getClass().getField(attribute).get(this).toString());
                                    } else {
                                        tuple.put(attribute, location.getClass().getField(attribute).get(this).toString());
                                    }
                                } catch (NoSuchFieldException e) {
                                    LOG.debug(attribute + " field is not returned by geo ip library. License issue?");
                                }
                            }
                        }

                    } catch (IllegalAccessException | IOException e) {
                        DTThrowable.rethrow(e);
                    } catch (GeoIp2Exception e) {
                        LOG.debug(lookupKey + " - can't resolve");
                    }
                }
                outputPort.emit(tuple);
            }
        }
    };

    @Override
    public void teardown() {
        try {
            reader.close();
        } catch (IOException e) {
            DTThrowable.rethrow(e);
        }
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
