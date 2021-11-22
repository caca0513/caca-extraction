package caca.extraction.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SROIEAnnotation {
    private final String company;
    private final String date;
    private final String address;
    private final String total;
}
