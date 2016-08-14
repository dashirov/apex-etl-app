/**
 * Put your copyright and license info here.
 */
package com.iaccap.data.apex.etl.app.api.adwords;

import com.datatorrent.api.DAG;
import com.datatorrent.api.StreamingApplication;
import com.datatorrent.api.annotation.ApplicationAnnotation;
import com.datatorrent.lib.io.ConsoleOutputOperator;
import org.apache.hadoop.conf.Configuration;

@ApplicationAnnotation(name = "Google AdWords Keywords Performance Report")
public class Application implements StreamingApplication {
    @Override
    public void populateDAG(DAG dag, Configuration conf) {
        AdWordsKeywordsPerformanceSingleCustomerInputOperator input = dag.addOperator("sampleDataEmitter", new AdWordsKeywordsPerformanceSingleCustomerInputOperator());
        // fill in appropriate values here

        input.setApiDeveloperToken("");  // identifies your application to AdWords API
        input.setApiClientId("");        // identifies your application to OAuth2
        input.setApiClientSecret("");    // authorizes your identity
        input.setApiRefreshToken("");    // as means of utility periodically regenerates accessToken issued by OAuth2
        input.setApiCustomerId("");      // single customerId account under MCC or not necessarily

        input.setApiUserAgent("Apache Apex Malhar AdWords Input Operator");

        ConsoleOutputOperator console = dag.addOperator("console", new ConsoleOutputOperator());
        dag.addStream("googleKeywordsToConsole", input.outputPort, console.input);
    }
}
