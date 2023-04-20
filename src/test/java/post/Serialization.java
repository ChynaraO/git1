package post;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import pojo.PetPojo;

import java.io.File;
import java.io.IOException;

public class Serialization {
    @Test
    public void serializationTest() throws IOException {
        PetPojo pet = new PetPojo();
        pet.setName("Hutch");
        pet.setStatus("Serving");
        pet.setId(78133);
        File jsonFile = new File("src/test/resources/pet.json");
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.writeValue(jsonFile, pet);
    }
    @Test
    public void serializationTest2(){           //request as a file
        RestAssured.baseURI="https://petstore.swagger.io";
        RestAssured.basePath="v2/pet";
        File jsonFile = new File("src/test/resources/pet.json");
        RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(jsonFile)
                .when()
                .post()
                .then()
                .statusCode(200)
                .and()
                .body("name", Matchers.is("Hutch"));

    }
    @Test
    public void serializationTest3(){           //request as a java object
        RestAssured.baseURI="https://petstore.swagger.io";
        RestAssured.basePath="v2/pet";
        PetPojo pet = new PetPojo();
        pet.setId(8908);
        pet.setName("Zeus");
        pet.setStatus("playing");
        RestAssured.given().accept(ContentType.JSON).contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body("name", Matchers.is("Zeus"))
                .and()
                .body("status", Matchers.is("playing"))
                .extract().response();
    }
}
