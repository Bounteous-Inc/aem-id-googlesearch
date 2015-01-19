package com.infield.googlesearch.servlet;

import com.day.cq.commons.TidyJSONWriter;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.CustomsearchRequestInitializer;
import com.google.api.services.customsearch.model.Search;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SlingServlet(generateComponent = false, paths = {"/bin/custom/searchConfig"}, methods = {"POST", "GET"})
@Component(label = "Google Search Service : Check Servlet", description = "Google Search Service Servlet used in the dialog to check if values are correct or not",
        enabled = true, immediate = true)
public class GoogleCustomSearchServlet extends SlingAllMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleCustomSearchServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            TidyJSONWriter jsonOut = new TidyJSONWriter(response.getWriter());

            jsonOut.setTidy(true);
            jsonOut.object();

            String apiKey = request.getParameter("apikey");
            String appName = request.getParameter("appname");
            String cx = request.getParameter("cx");

            jsonOut.key("api_key").value(apiKey);
            jsonOut.key("app_name").value(appName);
            jsonOut.key("cx").value(cx);

            if ((apiKey != null && !apiKey.equals("")) && (appName != null
                    && !appName.equals("")) && (cx != null && !cx.equals(""))) {
                try {
                    Customsearch customsearch = new Customsearch.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                            JacksonFactory.getDefaultInstance(), null)
                            .setApplicationName(appName)
                            .setGoogleClientRequestInitializer(new CustomsearchRequestInitializer(apiKey))
                            .build();

                    Customsearch.Cse.List list = customsearch.cse().list("test")
                            .setNum(new Long(2))
                            .setStart(new Long(1))
                            .setCx(cx);
                    Search results = list.execute();

                    if (results != null) {
                        jsonOut.key("success").value(true);
                        jsonOut.key("error").value(false);
                    }

                } catch (GeneralSecurityException e) {
                    jsonOut.key("error").value(true);
                    jsonOut.key("success").value(false);
                    LOGGER.error("---General Securtiy exception Occurred---" + e);
                }  catch (IOException e) {
                    jsonOut.key("error").value(true);
                    jsonOut.key("success").value(false);
                    LOGGER.error("---IO Exception---", e);
                }
            }
            jsonOut.endObject();
        } catch (IOException e) {
            LOGGER.info("---IO Exception occured---", e);
        }  catch (JSONException e) {
            LOGGER.info("---JSON exception occurred---", e);
        }
    }
}
