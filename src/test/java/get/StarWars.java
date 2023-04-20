package get;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.ResultPojo;
import pojo.StarWarsPojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StarWars {
    //1. Defined/determined the endpoint
    //2. Added query string params as needed
    //3. Defined HTTP Method
    //4. Send
    //5. Validate status code

    @Test
    public void getSWChars(){
        RestAssured.given().when().get("https://swapi.dev/api/people").then().statusCode(200).log().body();
    }
    @Test
    public void getSWCharDeserialized(){
        Response response = RestAssured.given().header("Accept", "application/json").when().
                get("https://swapi.dev/api/people").then().statusCode(200).extract().response();
            Map<String, Object> deserializedResponse = response.as(new TypeRef<Map<String, Object>>() {});
    int count = (int) deserializedResponse.get("count");
        Assert.assertEquals(82, count);
       // Array of Json objects[{},{},{},{}]
        List<Map<String, Object>> results = (List<Map<String, Object>>) deserializedResponse.get("results");
        System.out.println(results);
        int busket = 0;
        List<Object> listOfChar = new ArrayList<>(); // for names
        Map<String, String> onlyFemales = new HashMap<>(); //
        do{
            deserializedResponse=response.as(new TypeRef<Map<String, Object>>() {
            });
            List<Object> page = (List<Object>) deserializedResponse.get("results");
            for (int i =0; i<page.size(); i++){
                Map<String, String> person = (Map<String, String>) page.get(i);
                if((person.get("gender").equals("female"))){
                    onlyFemales.put(person.get("name"), person.get("gender") );
                }
                listOfChar.add(person.get("name"));
            }
            int countPeople = page.size();
            busket+=countPeople;
            if ((String)deserializedResponse.get("next")!=null){
                response = RestAssured.given().header("Accept", "application/json").when().
                        get((String)deserializedResponse.get("next")).then().statusCode(200).extract().response();
            }
        }while ((String)deserializedResponse.get("next")!=null);
        Assert.assertEquals(count, busket);
        System.out.println(onlyFemales);
        System.out.println(listOfChar);
    }




@Test
    public void swapGetWithPojo(){
    Response response = RestAssured.given().header("Accept", "application/json").when().get("https://swapi.dev/api/people").then()
            .statusCode(200).extract().response();
    StarWarsPojo deserializedResp = response.as(StarWarsPojo.class);
    int actualCount = deserializedResp.getCount();
    int expectedCount = 82;
    Assert.assertEquals(expectedCount,actualCount);
    List<ResultPojo> results = deserializedResp.getResults();
    for(ResultPojo result: results){
        System.out.println(result.getName());
    }
}
@Test
    public void starWarsTest(){
        RestAssured.baseURI="https://swapi.dev";
        RestAssured.basePath="api/people";
       // RestAssured.given().header("accept", "application/json") or
    //RestAssured.given().accept("application/json") or

        Response response = RestAssured.given().accept(ContentType.JSON).log().all() //prints only request
                .when().get().then()
                .statusCode(200).log().all().extract().response();        //prints only response
          StarWarsPojo parsedResponse = response.as(StarWarsPojo.class)   ;
    int actualTotalCharacterCount = parsedResponse.getResults().size(); //from first page
    String nextUrl = parsedResponse.getNext();  //gets value of "next" field from json response
    while(nextUrl!=null){
        // we need to make a get request to nextUrl
        // count character from next page and add to previous
        //get next pageUrl
        response=RestAssured.given().accept(ContentType.JSON).when().get(nextUrl)
                .then().statusCode(200).contentType(ContentType.JSON)  //validating response format is json
         .extract().response();
        parsedResponse=response.as(StarWarsPojo.class);
        actualTotalCharacterCount+=parsedResponse.getResults().size();
        nextUrl=parsedResponse.getNext();
    }
    //validating count equals to total number of characters
    Assert.assertEquals( parsedResponse.getCount(), actualTotalCharacterCount);


}
    //HW:
    //	- validate that SW API Count value is correct, we have total of 82 characters.
    //	- get list of all SW characters name
    //	- LVL100: Find only characters gender is female:
    //	Map<String, List<String>> -> female:


}



