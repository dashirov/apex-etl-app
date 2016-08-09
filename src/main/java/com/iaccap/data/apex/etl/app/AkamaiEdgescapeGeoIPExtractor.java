package com.iaccap.data.apex.etl.app;

import com.akamai.AkaData;
import com.datatorrent.lib.logs.InformationExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
/**
 * An implementation of InformationExtractor that extracts Geo information from an IP address using Akamai Edgescape API.
 * API jars are available for service customers at https://control.akamai.com/
 *<p>
 * IMPORTANT: The user of this extractor needs to include the jars which contain these classes in DAGContext.LIBRARY_JARS
 *
 * com.akamai.AkaData.class
 * @displayName Geo IP Extractor
 * @category Output
 * @tags extraction, geo
 * @since 0.9.4
 */

public class AkamaiEdgescapeGeoIPExtractor   implements InformationExtractor{

    private static final Logger LOG = LoggerFactory.getLogger(AkamaiEdgescapeGeoIPExtractor.class);
    private static final long serialVersionUID = 201608032100L;

    @NotNull
    private String serviceHost;

    @NotNull
    private int servicePort;

    @NotNull
    private int responseTimeout;

    private Map<String,String> mapReMap;

    private transient AkaData data;

    @Override
    public void setup() {
        LOG.debug("AkamaiEdgescapeGeoIPExtractor starting up");
        AkaData.set_aka_server(serviceHost,responseTimeout);
        AkaData.set_aka_port(servicePort);
    }

    @Override
    public void teardown() {
        LOG.debug("AkamaiEdgescapeGeoIPExtractor shutting down");
    }

    @Override
    public Map<String, Object> extractInformation(Object value)
    {
        // TODO: refuse to lookup local loopback and lan reserved ip addresses. Maintain a cache of previously seen
        // results to avoid UDP port congestion

        // TODO: maintain default_answer statistics

        // TODO: maintain latency statistics
        // Should this guy return JsonNode?

        Map<String, Object> m = new HashMap<String, Object>();
        try {
            data = new AkaData(value.toString(),responseTimeout);
            if (data != null) {
                for (int i=0; i<data.results.length; i++){
                    if ( mapReMap!=null && mapReMap.containsKey(data.results[i][0]) ) {
                        m.put(mapReMap.get(data.results[i][0]), data.results[i][1]);
                    }
                    else m.put(data.results[i][0],data.results[i][1]);
                }
            }
        }
        catch (Exception ex) {
            LOG.error("Caught exception when looking up Akamai Edgescape Geo IP for {}:", value, ex);
        }
        return m;
    }


    public String getServiceHost() {
        return serviceHost;
    }
    /**
     * Sets the GeoIP lookup service host name or ip address. Service running on localhost will have x10 throughput
     * when compared to load ballanced VIPs. Akamai Edgescape GeoIP lookup service runs on UDP ports, packets may get
     * lost and subsequently a timeout will trigger client default response
     * @param serviceHost response timeout will trigger a client default response
     */
    public void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public int getServicePort() {
        return servicePort;
    }
    /**
     * Sets the GeoIP lookup service port, default is 2001
     * @param servicePort response timeout will trigger a client default response
     */
    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public int getResponseTimeout() {
        return responseTimeout;
    }
    /**
     * Sets the GeoIP lookup response timeout in seconds.
     * @param responseTimeout response timeout will trigger a client default response
     */
    public void setResponseTimeout(int responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public Map<String, String> getMapReMap() {
        return mapReMap;
    }

    public void setMapReMap(Map<String, String> mapReMap) {
        this.mapReMap = mapReMap;
    }
}
