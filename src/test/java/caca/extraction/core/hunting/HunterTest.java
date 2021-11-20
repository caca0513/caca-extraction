package caca.extraction.core.hunting;

import caca.extraction.core.models.Area;
import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.models.Visible;
import caca.extraction.core.repo.impl.FileInstructionRepo;
import caca.extraction.core.service.impl.SROIELoader;
import caca.extraction.core.service.impl.SROIELoaderParameters;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.List;

class HunterTest {

    @Test
    void go() {
        var fn = "X51005453802.txt";
        var ocr_folder = "E:\\ML-Data\\SROIE2019\\0325updated.task1train(626p)";
        var anno_folder = "E:\\ML-Data\\SROIE2019\\0325updated.task2train(626p)";
        var inst_folder = "E:\\Projects\\Java\\TreasureHunting\\Instructions";
        var para = new SROIELoaderParameters();
        para.setOCRFolder(ocr_folder);
        para.setAnnotationFolder(anno_folder);
        var map = new SROIELoader(para).load(fn);

        var repo = new FileInstructionRepo();
        repo.setFolder(inst_folder);
        var insts = repo.load(fn);

        var hunter = HunterHub.recruit(map, insts);
        hunter.go();

        AssertExtractedField(map, hunter, "{Date}", "30/03/2018");
        AssertExtractedField(map, hunter, "{total}", "159.00");
    }

    private void AssertExtractedField(TreasureMap map, Hunter hunter, String field, String expectation) {
        var data = hunter.open(field, map);
        Assert.isTrue(data.size()==1, "only one "+ field + " should be found");
        Assert.isTrue(data.get(0) instanceof Visible, "the found "+ field + " should be an Visible");
        Assert.isTrue(((Visible) data.get(0)).getContent().equals(expectation), "the found "+ field + " should be " + expectation);
    }
}