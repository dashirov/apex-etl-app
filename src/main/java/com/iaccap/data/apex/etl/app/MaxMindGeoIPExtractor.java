package com.iaccap.data.apex.etl.app;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.io.IOException;
import javax.validation.constraints.NotNull;

import com.datatorrent.lib.logs.InformationExtractor;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of InformationExtractor that extracts Geo information from an IP address using maxmind API .
 *<p>
 *     Database path can be set to freshly downloaded file or use the one included with enricher
 *     A re-map map can be specified to avoid name clashes with existing keys.
 * IMPORTANT: The user of this extractor needs to include the jars which contain these classes in DAGContext.LIBRARY_JARS
 *
 * com.maxmind.geoip.LookupService.class
 * @displayName Geo IP Extractor
 * @category Output
 * @tags extraction, geo
 * @since 0.9.4
 */
public class MaxMindGeoIPExtractor implements InformationExtractor
{
    private static final Logger LOG = LoggerFactory.getLogger(MaxMindGeoIPExtractor.class);
    private static final long serialVersionUID = 201404221817L;
    private transient LookupService reader;
    /**
     * The local path that contains the maxmind "legacy" GeoIP db
     */
    private Map<String,String> mapReMap;
    private String databasePath;

    public String getDatabasePath()
    {
        return databasePath;
    }

    public void setDatabasePath(String databasePath)
    {
        this.databasePath = databasePath;
    }

    private final String[] knownAttributes={
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
    public void setup()
    {


        try {
            // if db bath is configured, use it
            if (databasePath!=null) {
                reader = new LookupService(databasePath, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
            } else {
                // else use the packaged in file last retrieved on 2016/09/06
                if ( this.getClass().getClassLoader().getResource("META-INF/GeoIP.dat") != null)
                  reader = new LookupService(new File(this.getClass().getClassLoader().getResource("META-INF/GeoIP.dat").toURI()), LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);
                else
                  throw new RuntimeException("GeoIP database not found in resources, nor configured explicitly.");
            }
        }
        catch (IOException |URISyntaxException|NullPointerException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void teardown()
    {
        reader.close();
    }

    @Override
    public Map<String, Object> extractInformation(Object value)
    {
        Map<String, Object> m = new HashMap<String, Object>();
        try {
            Location location = reader.getLocation(value.toString());
            if (location != null) {
                for (String attrubute: knownAttributes
                     ) {
                    if (mapReMap!=null && mapReMap.containsKey(attrubute)){
                        m.put(mapReMap.get(attrubute),location.getClass().getField(attrubute).get(this).toString());
                    } else
                        m.put(attrubute,location.getClass().getField(attrubute).get(this).toString());
                }
            }
        }
        catch (Exception ex) {
            LOG.error("Caught exception when looking up Geo IP for {}:", value, ex);
        }
        return m;
    }

}
