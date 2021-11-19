package caca.extraction.core.repo;

import caca.extraction.core.hunting.Instruction;
import caca.extraction.core.models.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface InstructionRepo  {
    List<Instruction> load(String path);
}
