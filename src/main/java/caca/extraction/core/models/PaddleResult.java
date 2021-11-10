package caca.extraction.core.models;

import lombok.Data;

@Data
public class PaddleResult {
    private Point lt;
    private Point rt;
    private Point rb;
    private Point lb;
    private String text;
    private String score;
}

@Data
class Point{
    private String x;
    private String y;
}