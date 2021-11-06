package caca.extraction.core.repo;

import caca.extraction.core.models.Area;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepo extends CrudRepository<Area, Long> {
}
