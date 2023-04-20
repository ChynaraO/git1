package post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.PetPojo;
import utils.PayloadUtils;

public class PetStore {
    @Test
    public void createPetTest(){
        RestAssured.baseURI="https://petstore.swagger.io";
        RestAssured.basePath="v2/pet";
        //POST https://petstore.swagger.io/v2/pet"
        //GET "https://petstore.swagger.io/v2/pet/567566"

        //Created  a pet
        //Validated POST call response body and status code
        //Sent GET request with newly created pet id
        //Validated GET call response body and status code

        String petName= "sharik";
        int petId = 567566;
        String petStatus= "waiting";
        Response response=RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)  // post request
                .body(PayloadUtils.getPetPayload(petId,petName,petStatus)).when().post().then().
                statusCode(200).extract().response();
        PetPojo parsedResponse = response.as(PetPojo.class);
        Assert.assertEquals(petName, parsedResponse.getName());
        Assert.assertEquals(petId, parsedResponse.getId());
        Assert.assertEquals(petStatus, parsedResponse.getStatus());
Response response1 = RestAssured.given().accept(ContentType.JSON)
        .when().get("https://petstore.swagger.io/v2/pet/"+petId)
        .then().statusCode(200).extract().response();
PetPojo parsedGetResponse1 = response1.as(PetPojo.class);
Assert.assertEquals(petId, parsedGetResponse1.getId());
        Assert.assertEquals(petName, parsedGetResponse1.getName());
    }
}
