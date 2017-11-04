package app.vo.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {

    private Long id;
    private String name;
    private String alt;
    private Map<String, String> avatars;
}
