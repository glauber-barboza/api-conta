package glauber.teste.apirest.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import glauber.teste.apirest.dto.Conta;
import glauber.teste.apirest.entity.ContaEntity;
import glauber.teste.apirest.repository.ContaRepository;
import glauber.teste.apirest.stub.ContaStub;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContaServiceTest {
    @Spy
    private ObjectMapper objectMapper;
    @InjectMocks
    private ContaService contaService;
    @Mock
    private ContaRepository contaRepository;

    @Before
    public void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void buscaContaTest() {
        ContaEntity entity = ContaStub.createEntity();
        when(contaRepository.findById(any())).thenReturn(Optional.of(entity));
        Conta conta = contaService.getConta(1L);
        assertNotNull(conta);
        assertEquals(conta.getAgencia(), entity.getAgencia());
        assertEquals(conta.getCpf(), entity.getCpf());
        assertEquals(conta.getNumero(), entity.getNumero());
        assertEquals(conta.getDataAtualizacao(), entity.getDataAtualizacao());
        assertEquals(conta.getDataCriacao(), entity.getDataCriacao());
        assertEquals(conta.getStatus(), entity.getStatus());
        assertEquals(conta.getNumero(), entity.getNumero());
    }

    @Test
    public void createContaTest() {
        Conta conta = objectMapper.convertValue(ContaStub.createContaRequest(), Conta.class);
        contaService.createConta(conta);

        ContaEntity contaEntity = capturarSave();

        assertNull(contaEntity.getDataAtualizacao());
        assertNotNull(contaEntity);
        assertNotNull(contaEntity.getDataCriacao());
        assertEquals(contaEntity.getAgencia(), conta.getAgencia());
        assertEquals(contaEntity.getCpf(), conta.getCpf());
        assertEquals(contaEntity.getNumero(), conta.getNumero());
        assertTrue(contaEntity.getStatus());
    }

    @Test
    public void updateContaTest() {
        Long id = 1L;
        ContaEntity entity = ContaStub.createEntity();
        when(contaRepository.findById(id)).thenReturn(Optional.of(entity));
        Conta conta = objectMapper.convertValue(ContaStub.createContaRequest(), Conta.class);
        contaService.updateConta(id, conta);

        ContaEntity contaEntity = capturarSave();
        assertNotNull(contaEntity.getDataAtualizacao());
        assertNotNull(contaEntity);
        assertNotNull(contaEntity.getDataCriacao());
        assertEquals(contaEntity.getAgencia(), conta.getAgencia());
        assertEquals(contaEntity.getCpf(), conta.getCpf());
        assertEquals(contaEntity.getNumero(), conta.getNumero());
        assertEquals(contaEntity.getIdConta(), id);
        assertTrue(contaEntity.getStatus());
    }

    @Test
    public void deleteContaTest() {
        Long id = 1L;
        ContaEntity entity = ContaStub.createEntity();
        when(contaRepository.findById(id)).thenReturn(Optional.of(entity));

        contaService.deleteConta(1L);
        ContaEntity contaEntity = capturarSave();
        assertFalse(contaEntity.getStatus());
    }

    private ContaEntity capturarSave() {
        ArgumentCaptor<ContaEntity> captor = ArgumentCaptor.forClass(ContaEntity.class);
        Mockito.verify(contaRepository, Mockito.atLeastOnce()).save(captor.capture());
        return captor.getValue();
    }
}
