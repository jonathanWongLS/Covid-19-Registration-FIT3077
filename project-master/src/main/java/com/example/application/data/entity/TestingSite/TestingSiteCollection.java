package com.example.application.data.entity.TestingSite;

import com.example.application.data.entity.HttpHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the testing site collection storing a list of TestingSite
 */
public class TestingSiteCollection {
    private List<TestingSite> collection = new ArrayList<TestingSite>();

    /**
     * Constructor of TestingSiteCollection
     */
    public TestingSiteCollection(){
        try {
            this.getSitesService();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * HTTP request to API to add a new testing site
     * @param name testing site name
     * @param description testing site description
     * @param websiteUrl testing site websiteUrl
     * @param phoneNumber testing site phoneNumber
     * @param latitude latitude of site
     * @param longitude longitude of site
     * @param unitNumber site unitNumber
     * @param street site street
     * @param street2 site street2
     * @param suburb site suburb
     * @param state site state
     * @param postcode site postcode
     * @param facilityType type of testing site
     * @param openTime open time of testing site
     * @param closeTime close time of testing site
     * @param waitingTime waiting time of testing site
     * @throws Exception for Error in request
     */
    public void addSiteService(String name, String description, String websiteUrl, String phoneNumber, int latitude, int longitude, String unitNumber, String street, String street2, String suburb, String state, String postcode, String facilityType, String openTime, String closeTime, String waitingTime) throws Exception{
        if (description!=null)
            description = "\""+description+ "\"";
        if (websiteUrl!=null)
            websiteUrl = "\""+websiteUrl+ "\"";
        if (phoneNumber!=null)
            phoneNumber = "\""+phoneNumber+ "\"";
        if (street2!=null)
            street2 = "\""+street2+ "\"";
        String jsonString = "{" +
                "\"name\":\"" + name + "\"," +
                "\"description\":" + description + "," +
                "\"websiteUrl\":" + websiteUrl + "," +
                "\"phoneNumber\":" + phoneNumber + "," +
                "\"address\": {"+
                "\"latitude\":" + latitude  +
                ",\"longitude\":" + longitude  +
                ",\"unitNumber\":\"" + unitNumber + "\"" +
                ",\"street\":\"" + street + "\"" +
                ",\"street2\":" + street2 +
                ",\"suburb\":\"" + suburb + "\"" +
                ",\"state\":\"" + state + "\"" +
                ",\"postcode\":\"" + postcode + "\"" +
                "},"+
                "\"additionalInfo\": {" +
                "\"facilityType\":\"" + facilityType + "\"" +
                ",\"openTime\":\"" + openTime + "\"" +
                ",\"closeTime\":\"" + closeTime + "\"" +
                ",\"waitingTime\":\"" + waitingTime + "\"" +
                "}"+
                "}";
        String testingSiteUrl = "https://fit3077.com/api/v2/testing-site";

        HttpResponse<String> response = new HttpHelper().postService(testingSiteUrl,jsonString);
    }

    /**
     * HTTP request to API to populate the list of collection
     * @throws Exception for Error in request
     */
    public void getSitesService() throws Exception{
        String testingSiteUrl = "https://fit3077.com/api/v2/testing-site";

        HttpResponse<String> response = new HttpHelper().getService(testingSiteUrl);

        // Error checking for this sample code. You can check the status code of your request, as part of performing error handling in your assignment.
        if (response.statusCode() != 200) {
            throw new Exception("Please specify your API key in line 21 to continue using this sample code.");
        }

        // The GET /user endpoint returns a JSON array, so we can loop through the response as we could with a normal array/list.
        ObjectNode[] jsonNodes = new ObjectMapper().readValue(response.body(), ObjectNode[].class);

        for (ObjectNode node: jsonNodes) {
            TestingSite site = new TestingSite(node.get("id").asText(),node.get("name").asText(),node.get("description").asText(),node.get("websiteUrl").asText(),node.get("phoneNumber").asText(),node.get("address").get("latitude").asDouble(),node.get("address").get("longitude").asDouble(),node.get("address").get("unitNumber").asInt(),node.get("address").get("street").asText(),node.get("address").get("street2").asText(),node.get("address").get("suburb").asText(),node.get("address").get("state").asText(),node.get("address").get("postcode").asText(),node.get("additionalInfo").get("facilityType").asText(),node.get("additionalInfo").get("openTime").asText(),node.get("additionalInfo").get("closeTime").asText(),node.get("additionalInfo").get("waitingTime").asText());
            collection.add(site);
        }
    }

    /**
     * Getter for collection of testing site
     * @return collection of testing site
     */
    public List<TestingSite> getCollection() {
        return collection;
    }

    /**
     * Refresh the collection
     * @return boolean to indicate if the update was successfully performed
     */
    public boolean updateCollection() {
        collection.clear();
        try {
            this.getSitesService();
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * Search the testing site collection
     * @param keyword keyword to search with
     * @return List of TestingSite that match the criteria
     */
    public List<TestingSite> searchCollection(String keyword) {
        List<TestingSite> result = new ArrayList<>();

        collection.forEach(testingSite -> {
            if (testingSite.getAddress().getSuburb().contains(keyword)||testingSite.getFacilityType().contains(keyword)) {
                result.add(testingSite);
            }
        });

        return result;
    }
}
