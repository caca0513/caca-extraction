package caca.extraction.core.repo.impl;

import caca.extraction.core.models.PaddleResult;
import caca.extraction.core.repo.PaddleOCRResultRepo;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Repository
public class PaddleOCRFileRepo implements PaddleOCRResultRepo {
    @Override
    public List<PaddleResult> getByName(String name) throws IOException {
        var result = JSON.parseArray(Files.readString(Path.of(name)), PaddleResult.class);
        return result;
    }
}
