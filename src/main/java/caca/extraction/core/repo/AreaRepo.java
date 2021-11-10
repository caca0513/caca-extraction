package caca.extraction.core.repo;

import caca.extraction.core.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepo extends JpaRepository<Area, Long> {
}
