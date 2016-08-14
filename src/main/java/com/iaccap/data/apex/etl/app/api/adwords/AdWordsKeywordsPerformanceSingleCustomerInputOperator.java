package com.iaccap.data.apex.etl.app.api.adwords;

import com.datatorrent.api.Context;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.InputOperator;
import com.datatorrent.netlet.util.DTThrowable;
import com.google.api.ads.adwords.lib.client.AdWordsSession;
import com.google.api.ads.adwords.lib.client.reporting.ReportingConfiguration;
import com.google.api.ads.adwords.lib.jaxb.v201607.DownloadFormat;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponse;
import com.google.api.ads.adwords.lib.utils.ReportDownloadResponseException;
import com.google.api.ads.adwords.lib.utils.ReportException;
import com.google.api.ads.adwords.lib.utils.v201607.ReportDownloader;
import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.util.Charsets;
import org.apache.commons.configuration.MapConfiguration;
import org.apache.commons.lang3.concurrent.TimedSemaphore;

import javax.validation.constraints.NotNull;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by dtadmin on 8/11/16.
 */

public class AdWordsKeywordsPerformanceSingleCustomerInputOperator implements InputOperator {


    private final int TOKEN_REFRESH_PERIOD = 1;
    public transient DefaultOutputPort<List<String>> outputPort = new DefaultOutputPort<List<String>>();
    public transient DefaultOutputPort<String> csvOutputPort = new DefaultOutputPort<String>();
    private transient TimedSemaphore loadEqualizer;
    private transient AdWordsSession adWordsSession;
    private transient Credential oAuth2Credential;
    private transient BufferedReader reader = null;


    private String apiEmail;
    private String apiPassword;


    @NotNull
    private String apiUserAgent = "Apache Apex Malhar AdWords Input Operator";


    @NotNull
    private String apiDeveloperToken;

    @NotNull
    private String apiClientId;

    @NotNull
    private String apiClientSecret;

    @NotNull
    private String apiRefreshToken;

    @NotNull
    private String apiCustomerId;

    @NotNull
    private String apiEnvironment = "production";

    @Override
    public void emitTuples() {
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                if (outputPort.isConnected())
                    outputPort.emit(new ArrayList<String>(Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"))));

                if (csvOutputPort.isConnected())
                    csvOutputPort.emit(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void beginWindow(long windowId) {

    }

    @Override
    public void endWindow() {

    }

    @Override
    public void setup(Context.OperatorContext context) {
        // example: https://github.com/googleads/googleads-java-lib/blob/master/examples/adwords_axis/src/main/java/adwords/axis/v201607/reporting/ParallelReportDownload.java
        try {
            Map<String, String> map = new HashMap<String, String>();

            /** developerToken identifies your application to the adwords api.
             * Only approved tokens can connect to real accounts.
             * Pending tokens can connect to test accounts.
             *
             * Once your token is approved, you can get access to all your accounts,
             * including those that are not linked to your MCC account.
             * */
            map.put("api.adwords.developerToken", apiDeveloperToken);

            /**
             * clientId and clientSecret map your application to a Google developer console
             * and are used for OAuth2 authentication
             */
            map.put("api.adwords.clientId", apiClientId);
            map.put("api.adwords.clientSecret", apiClientSecret);
            /**
             * The client library uses a refresh token to periodically regenerate OAuth2 accessToken
             */
            map.put("api.adwords.refreshToken", apiRefreshToken);
            map.put("api.adwords.refreshOAuth2Token", "true"); // TODO: if VM does not shutdown, track the thread responsible for refresh and shut it down in void teardown()

            /**
             * clientCustomerId is the account number of the customer account you want to manage via the API.
             * Optionally it can be left out of global configuration and set programmatically between API calls.
             * Once your developerToken is approved, you can use the real clientCustomerId and not just the test ones.
             */
            map.put("api.adwords.clientCustomerId", apiCustomerId);


            // TODO: expose configuration to Apex user, these are hardcoded values for testing purposes only
            map.put("api.adwords.userAgent", apiUserAgent);
            map.put("api.adwords.isPartialFailure", "false");
            map.put("api.adwords.returnMoneyInMicros", "true");
            map.put("api.adwords.includeUtilitiesInUserAgent", "true");

            // TODO: keys below are not well understood yet. What are they used for? Do we need them here?
            if (apiEnvironment != null)
                map.put("api.adwords.environment", apiEnvironment);
            if (apiEmail != null)
                map.put("api.adwords.email", apiEmail);
            if (apiPassword != null)
                map.put("api.adwords.password", apiPassword);


            /**
             * Service account - use json key file instead of client secret
             */
            // map.put("api.adwords.jsonKeyFilePath", "INSERT_PATH_TO_JSON_KEY_FILE_HERE");


            MapConfiguration configuration = new MapConfiguration(map);
            Credential oAuth2Credential = new OfflineCredentials.Builder()
                    .forApi(OfflineCredentials.Api.ADWORDS)
                    .from(configuration)
                    .build()
                    .generateCredential();


            // Construct an AdWordsSession.

            adWordsSession = new AdWordsSession.Builder()
                    .from(configuration)
                    .withOAuth2Credential(oAuth2Credential)
                    .build();

            String query =
                    "SELECT Date,CampaignId,CampaignName,AdGroupId,AdGroupStatus,AdGroupName,AdNetworkType1,AdNetworkType2,KeywordText,Impressions,Clicks " +
                            "FROM KEYWORDS_PERFORMANCE_REPORT WHERE STATUS IN [EBNABLED,PAUSED] DURING LAST_7_DAYS";


            ReportingConfiguration reportingConfiguration =
                    new ReportingConfiguration.Builder()
                            // Skip all header and summary lines since the loop below expects
                            // every field to be present in each line.
                            .skipReportHeader(true)
                            .skipColumnHeader(true)
                            .skipReportSummary(true)
                            // Enable to include rows with zero impressions.
                            .includeZeroImpressions(false)
                            .build();
            adWordsSession.setReportingConfiguration(reportingConfiguration);

            final ReportDownloadResponse response =
                    new ReportDownloader(adWordsSession).downloadReport(query, DownloadFormat.CSV);
            reader = new BufferedReader(new InputStreamReader(response.getInputStream(), Charsets.UTF_8));

        } catch (OAuthException | ValidationException | ReportDownloadResponseException | ReportException | NullPointerException e) {
            DTThrowable.rethrow(e);
        }
    }

    @Override
    public void teardown() {
        try {
            if (!loadEqualizer.isShutdown()) {
                loadEqualizer.shutdown();
            }
        } catch (Exception e) {
            DTThrowable.rethrow(e);
        }
    }


    // Getters and Setters

    /**
     * @return
     */
    public String getApiCustomerId() {
        return apiCustomerId;
    }

    /**
     * @param apiCustomerId
     */
    public void setApiCustomerId(String apiCustomerId) {
        this.apiCustomerId = apiCustomerId;
    }

    /**
     * @return
     */
    public String getApiPassword() {
        return apiPassword;
    }

    /**
     * @param apiPassword
     */
    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    /**
     * @return
     */
    public String getApiEmail() {
        return apiEmail;
    }

    /**
     * @param apiEmail
     */
    public void setApiEmail(String apiEmail) {
        this.apiEmail = apiEmail;
    }

    /**
     * @return
     */
    public String getApiUserAgent() {
        return apiUserAgent;
    }

    /**
     * @param apiUserAgent
     */
    public void setApiUserAgent(String apiUserAgent) {
        this.apiUserAgent = apiUserAgent;
    }

    /**
     * @return
     */
    public String getApiRefreshToken() {
        return apiRefreshToken;
    }

    /**
     * @param apiRefreshToken
     */
    public void setApiRefreshToken(String apiRefreshToken) {
        this.apiRefreshToken = apiRefreshToken;
    }

    /**
     * @return
     */
    public String getApiClientId() {
        return apiClientId;
    }

    /**
     * @param apiClientId
     */
    public void setApiClientId(String apiClientId) {
        this.apiClientId = apiClientId;
    }

    /**
     * @return
     */
    public String getApiClientSecret() {
        return apiClientSecret;
    }

    /**
     * @param apiClientSecret
     */
    public void setApiClientSecret(String apiClientSecret) {
        this.apiClientSecret = apiClientSecret;
    }

    /**
     * @return
     */
    public String getApiDeveloperToken() {
        return apiDeveloperToken;
    }

    /**
     * @param apiDeveloperToken
     */
    public void setApiDeveloperToken(String apiDeveloperToken) {
        this.apiDeveloperToken = apiDeveloperToken;
    }

    /**
     * @return
     */
    public String getApiEnvironment() {
        return apiEnvironment;
    }

    /**
     * @param apiEnvironment
     */
    public void setApiEnvironment(String apiEnvironment) {
        this.apiEnvironment = apiEnvironment;
    }
}
