package org.example.fullstacktodoapp.AiApi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureMockRestServiceServer
class AiApiServiceTest {
    @MockBean
    AiApiService aiService;

    @Autowired
    private MockMvc mockMvc;

    // todo: is this test still necessary when we're testing addTodo in the TodoService test?
    // what does this test add? it doesn't intercept any outside call
    @Test
    void spellcheckTodo() throws Exception {
        given(aiService.spellcheckTodo("description"))
                .willReturn("description");
        mockMvc.perform(post("/api/todo").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                                                {
                                                                    "description": "description",
                                                                    "status": "open"
                                                                }
                                """))
                .andExpect(status().isOk())
        .andExpect(content().string("description"));
    }

}