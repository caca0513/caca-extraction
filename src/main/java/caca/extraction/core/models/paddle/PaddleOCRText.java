package caca.extraction.core.models.paddle;

import lombok.Data;

@Data
public class PaddleOCRText {
    private Point lt;
    private Point rt;
    private Point rb;
    private Point lb;
    private String text;
    private String score;
}

