package caca.extraction.core.repo;

import caca.extraction.core.models.TreasureMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreasureMapRepo extends CrudRepository<TreasureMap, Long> {
}
