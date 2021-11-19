package caca.extraction.core.hunting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Indicator {
    private String all;
    private String direction;
    private String detail;
    private String offset;
    private String anchorName;

//    public Indicator(String all, String direction, String detail, String offset, String anchorName) {
//        this.all = all;
//        this.direction = direction;
//        this.detail = detail;
//        this.offset = offset;
//        this.anchorName = anchorName;
//    }
}
