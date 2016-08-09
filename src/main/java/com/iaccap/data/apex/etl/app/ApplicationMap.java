/**
 * Put your copyright and license info here.
 */
package com.iaccap.data.apex.etl.app;

import com.datatorrent.api.*;
import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.common.util.BaseOperator;
import com.datatorrent.lib.appdata.schemas.SchemaUtils;
import com.datatorrent.lib.io.ConsoleOutputOperator;
import com.google.common.collect.ImmutableMap;
import org.apache.hadoop.conf.Configuration;
import com.datatorrent.lib.stream.JsonByteArrayOperator;

import java.util.HashMap;
import java.util.Map;

@ApplicationAnnotation(name = "UnifiedLoggingETLApplicationV2")
public class ApplicationMap implements StreamingApplication {

    @Override
    public void populateDAG(DAG dag, Configuration conf) {


        JsonDataEmitterOperator input = dag.addOperator("sampleDataEmitter", new JsonDataEmitterOperator());
        JsonByteArrayOperator parser  = dag.addOperator("genericJsonParser", new JsonByteArrayOperator());
        MaxMindGeoIPOperator clientGeoIpEnricher = dag.addOperator("clientGeoIpEnchicher",new MaxMindGeoIPOperator());
        parser.setConcatenationCharacter('.');
        clientGeoIpEnricher.setLookupKey("clientIp");
        Map <String,String> clientGeoIPReMap = new HashMap<>();
        clientGeoIPReMap.put("countryCode","clientCountry");
        clientGeoIPReMap.put("city","clientCity");
        clientGeoIpEnricher.setMapReMap(clientGeoIPReMap);


        ConsoleOutputOperator console1 = dag.addOperator("console1", new ConsoleOutputOperator());
        //ConsoleOutputOperator console2 = dag.addOperator("console2", new ConsoleOutputOperator());
        //ConsoleOutputOperator console3 = dag.addOperator("console3", new ConsoleOutputOperator());


        dag.addStream("input", input.output, parser.input);
        dag.addStream("mapToGeoIP", parser.outputFlatMap, clientGeoIpEnricher.inputPort);
        dag.addStream("enrichedToConsole",clientGeoIpEnricher.outputPort,console1.input);
        //dag.addStream("parsedJson", parser.outputJsonObject, console2.input);
        //dag.addStream("parsedMap", parser.outputMap,console3.input);
    }

    public static class JsonDataEmitterOperator extends BaseOperator implements InputOperator {
        public static String jsonSample = "{\n" +
                "                \"application\": \"Thermometer\",\n" +
                "                \"applicationBuildVersion\": \"1.0.0\",\n" +
                "                \"applicationBuildDate\": \"2006-01-01\",\n" +
                "                \"eventName\": \"PageView\",\n" +
                "                \"eventId\": \"3AC3D4B1-4642-44C7-AE7A-DF8EC8709C9F\",\n" +
                "                \"eventTimestamp\": \"2015-11-04T09:00:31.234Z\",\n" +
                "                \"httpMethod\": \"GET\",\n" +
                "                \"httpUrl\": \"http://www.thermometer.com/#/\",\n" +
                "                \"clientBrowserLanguage\": \"en-US\",\n" +
                "                \"httpResponseCode\": 204,\n" +
                "                \"clientIp\": \"98.139.183.24\",\n" +
                "                \"serverName\": \"fe1.thermometer.net\",\n" +
                "                \"clientUserAgent\": \"Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36\",\n" +
                "                \"httpResponseTime\": 1,\n" +
                "                \"httpResponseSize\": 0,\n" +
                "                \"uniqueUserId\": \"3AC3D4B1-4642-44C7-AE7A-DF8EC8709C9F\",\n" +
                "                \"clientScreenWidth\": 1360,\n" +
                "                \"clientScreenHeight\": 768,\n" +
                "                \"clientScreenColorDepth\": 24,\n" +
                "                \"backFillRequired\": false,\n" +
                "                \"eventDetail\":{\"thisIsJson\":true,\"key\":{\"key\":\"value\",\"intKey\":12223,\"arrKey\":[1,\"one\",true]}}\n"+
                "}";

        public final transient DefaultOutputPort<byte[]> output = new DefaultOutputPort<byte[]>();

        @Override
        public void emitTuples() {
            //for (int i = 0; i <= 2; i++)
                output.emit(jsonSample.getBytes());
        }
    }

}
