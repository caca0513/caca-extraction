DELETE FROM Area;
DELETE FROM TreasureMap;

INSERT INTO TreasureMap (id, name) values (1, 'first');

INSERT INTO Area (id, TreasureMap_id, type, "left", "top", "right", "bottom", content)
          VALUES
                (1, 1, 'Visible', 0.1, 0.1, 0.5,  0.2, 'world'),
                (2, 1, 'Visible', 0.6, 0.1, 1.0,  0.2, 'hello'),
                (3, 1, 'Visible', 0.1, 0.3, 0.45, 0.4, 'let'),
                (4, 1, 'Visible', 0.5, 0.3, 0.95, 0.4, 'tangle');