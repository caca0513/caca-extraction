package caca.extraction.core.hunting;

import caca.extraction.core.models.TreasureMap;
import caca.extraction.core.repo.impl.FileInstructionRepo;
import caca.extraction.core.service.impl.SROIELoader;
import caca.extraction.core.service.impl.SROIELoaderParameters;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HunterTest {

    static FileInstructionRepo instRepo;
    static SROIELoader sroie;

    @BeforeAll
    static void setup() {
        var ocr_folder = "E:\\ML-Data\\SROIE2019\\0325updated.task1train(626p)";
        var anno_folder = "E:\\ML-Data\\SROIE2019\\0325updated.task2train(626p)";
        var inst_folder = "E:\\Projects\\Java\\TreasureHunting\\Instructions";
        var para = new SROIELoaderParameters();
        para.setOCRFolder(ocr_folder);
        para.setAnnotationFolder(anno_folder);
        sroie = new SROIELoader(para);
        instRepo = new FileInstructionRepo();
        instRepo.setFolder(inst_folder);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "X51005453802",
            "X51005453804"
    })
    void run_inst_X51005453802(String test) {
        var fn = "X51005453802";
        TestHunting(test, fn);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "X51005361883",
            "X51005361897",
            "X51005361898",
            "X51005361900",
            "X51005361907",
    })
    void run_inst_X51005361883(String test) {
        var fn = "X51005361883";
        TestHunting(test, fn);
    }

    private static void TestHunting(String test, String instructionSource) {
        var insts = instRepo.load(instructionSource);
        var anno = sroie.loadAnnotation(test);
        var map = sroie.load(test);

        var hub = new HunterHub();
        var hunter = hub.recruit(map, insts);
        hunter.go();

        AssertExtractedField(map, hunter, "{date}", anno.getDate());
        AssertExtractedField(map, hunter, "{total}", anno.getTotal());
        AssertExtractedField(map, hunter, "{company}", anno.getCompany());
        AssertExtractedField(map, hunter, "{address}", anno.getAddress());
    }

    private static void AssertExtractedField(TreasureMap map, Hunter hunter, String field, String expectation) {
        var treasure = hunter.open(field, map);
        Assertions.assertThat(treasure).isEqualTo(expectation).withFailMessage(field);
    }

}