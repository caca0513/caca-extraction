package caca.extraction.core.repo;

import caca.extraction.core.models.TreasureMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreasureMapRepo extends JpaRepository<TreasureMap, Long> {
}
