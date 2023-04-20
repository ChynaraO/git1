package put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class PetStore {
    /*
    1. Post call to create a pet
    2. Deserialize and validate POST response
    3. PUT Call to update an existing pet
    4. Deserialize and validate PUT response
    5. GET call to search for our pet
    6. Deserialize and validate GET response
     */
    @Test
    public void updatePetStore(){
        RestAssured.baseURI="https://petstore.swagger.io";
        RestAssured.basePath="v2/pet";
        int petId = 555555;
        String petName = "Bobik";
        String petStatus = "playing";
        RequestSpecification reqSpec = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON);
        //1. Post call to create a pet
        Response response = reqSpec
                .body(PayloadUtils.getPetPayload(petId, petName, petStatus))
                .when().post()
                .then().statusCode(200).extract().response();
        //2. Deserialize and validate POST response
        PetPojo parseResponse = response.as(PetPojo.class);
        Assert.assertEquals(petId, parseResponse.getId());
        Assert.assertEquals(petName, parseResponse.getName());
        Assert.assertEquals(petStatus, parseResponse.getStatus());
        //3. PUT Call to update an existing pet
        String newStatus = "sleeping";
        response= reqSpec
                .body(PayloadUtils.getPetPayload(petId,petName,newStatus))
                .when().put()
                .then().statusCode(200)
                .extract().response();
       //  4. Deserialize and validate PUT response
       parseResponse = response.as(PetPojo.class) ;
        Assert.assertEquals(petName, parseResponse.getName());
       // 5. GET call to search for our pet
        //POST URL https://petstore.swagger.io/v2/pet
        //PUT URL https://petstore.swagger.io/v2/pet/{petId}

        response = reqSpec
                .when().get(String.valueOf(petId))
                .then().statusCode(200).extract().response();
        //6. Deserialize and validate GET response
        parseResponse = response.as(PetPojo.class);
        Assert.assertEquals(petName, parseResponse.getName());
        Assert.assertEquals(petId, parseResponse.getId());
        Assert.assertEquals(newStatus, parseResponse.getStatus());
    }

}
