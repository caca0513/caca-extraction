package caca.extraction.core.service;

import caca.extraction.core.models.TreasureMap;

public interface MapLoader <TSource> {
    TreasureMap load(TSource source);
}
