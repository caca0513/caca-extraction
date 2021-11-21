package caca.extraction.core.repo;

import caca.extraction.core.hunting.Instruction;

import java.util.List;


public interface InstructionRepo {
    List<Instruction> load(String path);
}
