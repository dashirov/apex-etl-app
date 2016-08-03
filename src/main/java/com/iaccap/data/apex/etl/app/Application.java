/**
 * Put your copyright and license info here.
 */
package com.iaccap.data.apex.etl.app;

import com.datatorrent.api.*;
import com.datatorrent.common.util.BaseOperator;
import com.datatorrent.contrib.parser.JsonParser;
import com.datatorrent.lib.appdata.schemas.SchemaUtils;
import org.apache.hadoop.conf.Configuration;

import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.api.DAG.Locality;
import com.datatorrent.lib.io.ConsoleOutputOperator;

@ApplicationAnnotation(name="UnifiedLoggingETLApplication")
public class Application implements StreamingApplication
{

  @Override
  public void populateDAG(DAG dag, Configuration conf)
  {
    // Sample DAG with 2 operators
    // Replace this code with the DAG you want to build

    JsonDataEmitterOperator input = dag.addOperator("data", new JsonDataEmitterOperator());
    JsonParser parser = dag.addOperator("jsonparser", new JsonParser());

    parser.setClazz(ULEvent.class);
    dag.getMeta(parser).getMeta(parser.out).getAttributes().put(Context.PortContext.TUPLE_CLASS, ULEvent.class);
    parser.setJsonSchema(SchemaUtils.jarResourceFileToString("json-parser-schema.json"));
    ConsoleOutputOperator jsonObjectOp = dag.addOperator("jsonObjectOp", new ConsoleOutputOperator());
    ConsoleOutputOperator pojoOp = dag.addOperator("pojoOp", new ConsoleOutputOperator());
    jsonObjectOp.setDebug(true);
    dag.addStream("input", input.output, parser.in);
    dag.addStream("output", parser.parsedOutput, jsonObjectOp.input);
    dag.addStream("pojo", parser.out, pojoOp.input);
  }

    public static class JsonDataEmitterOperator extends BaseOperator implements InputOperator
    {
        public static String jsonSample = "{\n" +
                "                \"application\": \"Thermometer\",\n" +
                "                \"applicationBuildVersion\": \"1.0.0\",\n" +
                "                \"applicationBuildDate\": \"2006-01-01\",\n" +
                "                \"eventType\": \"PageView\",\n" +
                "                \"eventTimestamp\": \"2015-11-04T09:00:31.234Z\",\n" +
                "                \"httpMethod\": \"GET\",\n" +
                "                \"httpUrl\": \"http://www.thermometer.com/#/\",\n" +
                "                \"clientBrowserLanguage\": \"en-US\",\n" +
                "                \"httpResponseCode\": 204,\n" +
                "                \"clientIp\": \"102.93.156.12\",\n" +
                "                \"serverName\": \"fe1.thermometer.net\",\n" +
                "                \"clientUserAgent\": \"Mozilla/5.0 (Windows NT 6.2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36\",\n" +
                "                \"httpResponseTime\": 1,\n" +
                "                \"httpResponseSize\": 0,\n" +
                "                \"uniqueUserId\": \"3AC3D4B1-4642-44C7-AE7A-DF8EC8709C9F\",\n" +
                "                \"clientScreenWidth\": 1360,\n" +
                "                \"clientScreenHeight\": 768,\n" +
                "                \"clientScreenColorDepth\": 24,\n" +
                "                \"backFillRequired\": false\n" +
                "}";

        public final transient DefaultOutputPort<byte[]> output = new DefaultOutputPort<byte[]>();

        @Override
        public void emitTuples()
        {
            output.emit(jsonSample.getBytes());
        }
    }

}
