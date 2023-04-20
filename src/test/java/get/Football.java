package get;

import groovy.xml.StreamingDOMBuilder;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pojo.FootballPojo;
import pojo.FootballResultsPogo;

import java.util.List;
import java.util.Map;

public class Football {
    @Before
    public void setup(){
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";
    }
    @Test
    public void competitionTest(){
        RestAssured.given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("count", Matchers.equalTo(168))
                .and()
                .body("competitions[0].name", Matchers.equalTo("WC Qualification CAF"));
                //.extract().response();
    }
    @Test
    public void competitionTest1(){
        //GET RestAssured.baseURI = "http://api.football-data.org";
        //        RestAssured.basePath = "v2/competitions";
        //Parse the response
        //Search for MLS
        //validation that MLS competition id=2145
        RestAssured.baseURI = "http://api.football-data.org";
         RestAssured.basePath = "v2/competitions";
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
        .extract().response();
        FootballPojo footballPojo = response.as(FootballPojo.class);
        List<FootballResultsPogo> list = footballPojo.getCompetitions();
        for(int i = 0 ; i< list.size(); i++){
            if(list.get(i).getName().equals("MLS")){
                Assert.assertEquals(2145,list.get(i).getId() ) ;
            }
        }
        }
        @Test
                public void advancedRestAssuredTest(){
            RestAssured.baseURI = "http://api.football-data.org";
            RestAssured.basePath = "v2/competitions";
        ///////////////////Advanced RestAssured way
        Response response1 = RestAssured.given()
                .accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("competitions.find{it.name == 'MLS'}.id", Matchers.equalTo(2145))
                //find will apply only for the list, JasonArray
                .extract().response();
    }
    @Test
    public void advancedRestAssuredTest2(){
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";
        ///////////////////Advanced RestAssured way
        Response response1 = RestAssured.given()
                .accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when()
                .get()
                .then()
                .log().body()
                .statusCode(200)
                .body("competitions.collect{it.name}", Matchers.containsInRelativeOrder("Supercopa Argentina"))

                .extract().response();
        List<String> result = response1.path("competitions.collect{it.name}");
        Assert.assertEquals(168, result.size());
    }
    @Test
    public void advancedRestAssuredTest3() {
        RestAssured.baseURI = "http://api.football-data.org";
        RestAssured.basePath = "v2/competitions";
        //GET all country names where competitions ID is greater 2006
        Response response1 = RestAssured.given()
                .accept(ContentType.JSON)
                .header("X-Auth-Token", "c55b7a64e8424d46a52051bce36d1c0a")
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("competitions.collect{it.name}", Matchers.containsInRelativeOrder("Supercopa Argentina"))

                .extract().response();
        List<String> result = response1.path("competitions.collect{it.name}");
        Assert.assertEquals(168, result.size());

        List<String> countryNames = response1.path("competitions.findAll{it.id > 2006}.area.name");
        System.out.println(countryNames.size());
        //System.out.println(countryNames);

        //sum all id values for all competitions
        int sum =response1.path("competitions.collect{it.id}.sum()");
        System.out.println(sum);
    }
}
