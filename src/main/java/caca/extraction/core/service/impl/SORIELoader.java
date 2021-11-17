package caca.extraction.core.service.impl;

import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.service.MapLoader;
import org.springframework.stereotype.Service;

@Service
public class SORIELoader implements MapLoader<String> {

    private String ocrFolder;
    private String annotationFolder;

    public SORIELoader(SORIELoaderParameters parameters) {
        this.ocrFolder = parameters.getOCRFolder();
        this.annotationFolder = parameters.getAnnotationFolder();
    }

    @Override
    public TreasureMap load(String s) {
        return null;
    }
}
