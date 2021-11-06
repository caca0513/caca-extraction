package caca.extraction.web;

import caca.extraction.web.controllers.TreasureMapController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TreasureMapController.class)
class TreasureMapControllerTest {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    void homeTest() throws Exception {
//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("home"))
//                .andExpect(content().string(containsString("tangle")))
//        ;
//
//    }

}