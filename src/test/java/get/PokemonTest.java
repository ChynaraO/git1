package get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.pokemon.*;

import java.util.*;

public class PokemonTest {
    /*
    1. GET https://pokeapi.co/api/v2/pokemon?limit=100
2. Deserialize response using POJO classes
3. Validate count = 1279
4. Find url for pikachu
5. Validate that we got 100 pokemons
     */
    @Test
    public void pikachuTest(){
        RestAssured.baseURI="https://pokeapi.co/api/v2/pokemon";
        Response response = RestAssured.given().header("Accept", "application/json").
                queryParam("limit", "100").when()
                .get().then()
                .statusCode(200).extract().response();
        PokemonPojo deserializedResponse  = response.as(PokemonPojo.class);
        List<PokemonResultPojo> results = deserializedResponse.getResults();
        Assert.assertEquals(100, results.size());
        for(PokemonResultPojo pokemon : results){
            if(pokemon.getName().equalsIgnoreCase("pikachu")){
                System.out.println(pokemon.getUrl());
            }
        }
        /*
        1. GET https://pokeapi.co/api/v2/pokemon
2. Validate you got 20 pokemons
3. Get every pokemons ability and store those in Map<String, List<String>>
         */
    }
    @Test
    public void pokemonsTest(){
        RestAssured.baseURI="https://pokeapi.co/api/v2/pokemon";
        Response response = RestAssured.given().header("Accept", "application/json")
                .queryParam("limit", "20").when().get().then().statusCode(200).extract().response();

        PokemonPojo deserializedResponse = response.as(PokemonPojo.class);
        List <PokemonResultPojo> listOfResult = deserializedResponse.getResults();
int actualResultCount = 0;
List<String> listOfUrl = new ArrayList<>();
for(PokemonResultPojo p : listOfResult){
    actualResultCount++;
    listOfUrl.add(p.getUrl());
}
        int expected = 20;
        Assert.assertEquals(actualResultCount,expected);
        List<String> listOFAbilitiesOfPok = new ArrayList<>();
        for(String s : listOfUrl){
            RestAssured.baseURI=s;
            Response response1 = RestAssured.given().header("Accept", "application/json")
                    .when().get().then().statusCode(200).extract().response();
            PokemonDescriptionPojo1 deserializedDescription = response1.as(PokemonDescriptionPojo1.class);



        }
        Map <String, List<String>> abilitiesOfEachPokem = new HashMap<>();
for (int i = 0; i< listOfResult.size(); i++){
    abilitiesOfEachPokem.put(String.valueOf(listOfResult.get(i)), listOFAbilitiesOfPok);
    System.out.println(abilitiesOfEachPokem);

}


    }
    @Test
    public void homeWorkPokemon(){
        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .when().get("https://pokeapi.co/api/v2/pokemon")
                .then()
                .statusCode(200)
                .extract().response();
        PokemonPojo parserResponse = response.as(PokemonPojo.class);
        List<PokemonResultPojo> pokemons = parserResponse.getResults();
        Assert.assertEquals(20, pokemons.size());
        Map<String, List<String>> res = new LinkedHashMap<>();
        for(int i = 0; i<pokemons.size(); i++){
            Response response1 = RestAssured.given()
                    .accept(ContentType.JSON)
                    .when().get(pokemons.get(i).getUrl())
                    .then().statusCode(200)
                    .extract().response();
            EachPokemonPojo parsedResponse1 = response1.as(EachPokemonPojo.class);
            List<PokemonAbilitiesPojo> abilities = parsedResponse1.getAbilities();
            List<String> eachAbility = new ArrayList<>();
            String pokemonName = parserResponse.getResults().get(i).getName();
            for(int j = 0; j<abilities.size(); j++) {
                eachAbility.add(parsedResponse1.getAbilities().get(j).getAbility().getName());
            }
            res.put(pokemonName, eachAbility);


        }System.out.println(res);
    }

}
