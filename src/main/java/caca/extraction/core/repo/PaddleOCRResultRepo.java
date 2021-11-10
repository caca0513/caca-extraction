package caca.extraction.core.repo;

import caca.extraction.core.models.PaddleResult;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public interface PaddleOCRResultRepo  {
    List<PaddleResult> getByName(String name) throws IOException;
}
