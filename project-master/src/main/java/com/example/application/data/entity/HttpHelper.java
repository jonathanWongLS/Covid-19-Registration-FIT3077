package com.example.application.data.entity;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * This is a helper class for sending HTTP request to the API which minimise code redundancy
 */
public class HttpHelper {
    private final String APIKEY;

    /**
     * Constructor of HttpHelper
     */
    public HttpHelper(){
        APIKey apiKeyObj = new APIKey();
        APIKEY = apiKeyObj.getAPIKey();
    }

    /**
     * Send POST request to API
     * @param url url of the request
     * @param jsonString content of the request
     * @return response from API
     * @throws Exception for Error in request
     */
    public HttpResponse<String> postService(String url, String jsonString) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url))
                .setHeader("Authorization", APIKEY)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Sent GET request to API
     * @param url url of the request
     * @return response from API
     * @throws Exception for Error in request
     */
    public HttpResponse<String> getService(String url) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url))
                .setHeader("Authorization", APIKEY)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Send PATCH request to API
     * @param url url of the request
     * @param jsonString content of the request
     * @param id id of the data to update the content
     * @return response from API
     * @throws Exception for Error in request
     */
    public HttpResponse<String> patchService(String url, String jsonString, String id) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url+"/"+id))
                .setHeader("Authorization", APIKEY)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .method("PATCH",HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Send DELETE request to API
     * @param url url of the request
     * @param id id of the data to update the content
     * @return response from API
     * @throws Exception for Error in request
     */
    public HttpResponse<String> deleteService(String url, String id) throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(url+"/"+id))
                .setHeader("Authorization", APIKEY)
                .header("Content-Type","application/json") // This header needs to be set when sending a JSON request body.
                .DELETE()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
