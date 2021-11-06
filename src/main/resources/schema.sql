DROP TABLE IF EXISTS Area;

CREATE TABLE IF NOT EXISTS Area (
    id identity,
    type VARCHAR(200) NOT NULL,
    content VARCHAR(200) NOT NULL,
    "left" DOUBLE NOT NULL,
    "top" DOUBLE NOT NULL,
    "bottom" DOUBLE NOT NULL,
    "right" DOUBLE NOT NULL,
    TreasureMap_id LONG NOT NULL
);

DROP TABLE IF EXISTS TreasureMap;

CREATE TABLE IF NOT EXISTS TreasureMap (
    id identity,
    name VARCHAR(200) NOT NULL
);