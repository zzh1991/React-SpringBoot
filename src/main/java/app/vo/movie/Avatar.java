package app.vo.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static app.constant.Constant.SEPARATOR;

/**
 * @author zhihao zhang
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Avatar {

    private Long id;
    private String name;
    private String alt;
    private Map<String, String> avatars;

    public static String getNames(List<Avatar> avatars) {
        return avatars.stream()
                .map(Avatar::getName)
                .collect(Collectors.joining(SEPARATOR));
    }
}
