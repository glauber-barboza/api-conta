package glauber.teste.apirest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import glauber.teste.apirest.controller.ContaApi;
import glauber.teste.apirest.controller.request.ContaRequest;
import glauber.teste.apirest.service.ContaService;
import glauber.teste.apirest.stub.ContaStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ContaApi.class)
public class ContaApiTest {
    @Autowired
    protected MockMvc mockMvc;
    @Spy
    private ObjectMapper objectMapper;
    @MockBean
    private ContaService contaService;

    @Test
    public void getContaTest() throws Exception {
        when(contaService.getConta(anyLong())).thenReturn(ContaStub.create());
        mockMvc.perform(
                get("/v1/conta/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getContaPageTest() throws Exception {
        when(contaService.getPageConta(any())).thenReturn(ContaStub.createContaPage());

        mockMvc.perform(
                get("/v1/conta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postContaPageTest() throws Exception {
        ContaRequest contaRequest = ContaStub.createContaRequest();
        doNothing().when(contaService).createConta(any());
        String jsonInString = objectMapper.writerWithView(ContaRequest.class).writeValueAsString(contaRequest);
        mockMvc.perform(
                post("/v1/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInString))
                .andExpect(status().isOk());
    }

    @Test
    public void putContaPageTest() throws Exception {
        ContaRequest contaRequest = ContaStub.createContaRequest();
        doNothing().when(contaService).updateConta(any(), any());
        String jsonInString = objectMapper.writerWithView(ContaRequest.class).writeValueAsString(contaRequest);
        mockMvc.perform(
                put("/v1/conta/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInString))
                .andExpect(status().isOk());
    }
}
