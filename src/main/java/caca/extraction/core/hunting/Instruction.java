package caca.extraction.core.hunting;

import caca.extraction.core.hunting.actions.Action;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Instruction {
    private List<Indicator> indicators = new ArrayList<>();
    private Action action;
}
