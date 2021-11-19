package caca.extraction.core.hunting;

import caca.extraction.core.repo.impl.FileInstructionRepo;
import caca.extraction.core.service.impl.SROIELoader;
import caca.extraction.core.service.impl.SROIELoaderParameters;
import org.junit.jupiter.api.Test;

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

        var hunter = new Hunter();
        hunter.go(insts, map);
    }
}