package caca.extraction.core.repo.impl;

import caca.extraction.core.hunting.Instruction;
import caca.extraction.core.hunting.actions.DefineAction;
import caca.extraction.core.hunting.actions.FindAction;
import caca.extraction.core.hunting.Indicator;
import caca.extraction.core.repo.InstructionRepo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@ConfigurationProperties(prefix = "instruction.repo")
@Data
public class FileInstructionRepo implements InstructionRepo {

    static String lineNoPattern = "(?<no>\\d+\\. )?";
    static String variablePattern = "(?<variable>\\{.+?\\})";
    static Pattern actionPattern = Pattern.compile(lineNoPattern + "(?<action>(find|is) )" + variablePattern, Pattern.CASE_INSENSITIVE);
    static Pattern indicatorPattern = Pattern.compile(lineNoPattern + "(?<all>All )?(?<direction>above|below|left|right) to (?<detail>the (top|left|bottom|right) (?<offset>([+\\-])\\d\\.\\d+? of )?)?" + variablePattern, Pattern.CASE_INSENSITIVE);
    private String folder;

    private Instruction convert(String line) {
        line = line.trim();
        if (line.isEmpty() || line.startsWith("#"))
            return null;

        //remove leading line number like 1.  2.  3.
        //line = line.replaceAll("^\\d+\\.\\s+", "");

        var segments = line.split(",");
        var result = new Instruction();
        for (var seg : segments) {
            seg = seg.trim();
            if (!tryAddIndicator(result, seg))
                tryAddAction(result, seg);
        }

        return result;
    }

    private boolean tryAddAction(Instruction result, String seg) {
        var m = actionPattern.matcher(seg);
        if (!m.matches())
            return false;

        var action = m.group("action");
        var variable = m.group("variable");

        action = action.trim().toLowerCase();
        switch (action) {
            case "find":
                result.setAction(new FindAction(variable));
                break;
            case "is":
                result.setAction(new DefineAction(variable));
                break;
        }

        return true;
    }

    private boolean tryAddIndicator(Instruction result, String seg) {
        var m = indicatorPattern.matcher(seg);
        if (!m.matches())
            return false;

        var all = m.group("all");
        var direction = m.group("direction");
        var detail = m.group("detail");
        var offset = m.group("offset");
        var variable = m.group("variable");

        var ind = new Indicator(all, direction, detail, offset, variable);
        result.getIndicators().add(ind);

        return true;
    }

    @Override
    public List<Instruction> load(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Path.of(folder, path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return lines.stream().map(this::convert)
                .filter(Objects::nonNull).collect(Collectors.toList());
    }
}
