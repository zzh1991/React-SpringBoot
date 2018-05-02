package app.vo.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhihao zhang
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    private int max;
    private double average;
    private int stars;
    private int min;
}
