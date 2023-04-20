package delete;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import put.PetStore;

public class Petstore {
    @Test
    public void deletePetTest(){
        put.PetStore petStore = new put.PetStore();
        petStore.updatePetStore();
        RestAssured.baseURI= "https://petstore.swagger.io";
        RestAssured.basePath="v2/pet/555555";
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().delete()
                .then().statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String responseMess = jsonPath.getString("message");
        Assert.assertEquals("555555", responseMess);


    }
}
