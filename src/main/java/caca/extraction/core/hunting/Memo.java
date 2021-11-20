//package caca.extraction.core.hunting;
//
//import caca.extraction.core.models.Area;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class Memo {
//
//    private final Hunter hunter;
//
//    public Memo(Hunter hunter) {
//        this.hunter = hunter;
//    }
//
//    public Memo cloneFor(Hunter hunter) {
//        var result = new Memo(hunter);
//        result.anchors.putAll(anchors);
//        result.logs.addAll(logs);
//        return result;
//    }
//
//    public Map<String, Area> getAnchors() {
//        return anchors;
//    }
//
//    public Hunter getHunter() {
//        return hunter;
//    }
//}
