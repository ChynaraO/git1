package pojo.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import pojo.pokemon.PokemonDescriptionPojo1;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter

public class PokemonResultPojo {
    private String name;
    private String url;
    private List<EachPokemonPojo> pokemonDescriptionPojos;



}
