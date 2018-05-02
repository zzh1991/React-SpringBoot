package app.vo.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhihao zhang
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieVo {
    private int count;
    private int start;
    private int total;
    private List<Movie> subjects;
    private String title;
}
