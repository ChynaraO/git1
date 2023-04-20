package pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
@Setter
@Getter
public class StarWarsPojo {
    private int count ;
    private String next;
    private String previous;
    private List<Map<String, Object>> maps;
    private List<ResultPojo> results;

}
