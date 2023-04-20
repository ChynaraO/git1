package pojo.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class PokemonPojo {
    private int count;
    private String next;
    private String previous;
    private List<PokemonResultPojo> results;

}
