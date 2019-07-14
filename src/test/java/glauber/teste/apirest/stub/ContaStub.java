package glauber.teste.apirest.stub;

import glauber.teste.apirest.controller.request.ContaRequest;
import glauber.teste.apirest.dto.Conta;
import glauber.teste.apirest.dto.PageOut;
import glauber.teste.apirest.entity.ContaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.geo.GeoPage;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ContaStub {

    public static Conta create() {
        Conta conta = new Conta();
        conta.setIdConta(1L);
        conta.setAgencia("1234");
        conta.setNumero("123456");
        conta.setCpf("123456789000");
        conta.setDataAtualizacao(LocalDateTime.of(2019, 01, 01, 12, 12, 12));
        conta.setDataCriacao(LocalDateTime.of(2019, 01, 01, 12, 12, 12));
        return conta;
    }

    public static ContaEntity createEntity() {
        ContaEntity conta = new ContaEntity();
        conta.setIdConta(1L);
        conta.setStatus(true);
        conta.setAgencia("1234");
        conta.setNumero("123456");
        conta.setCpf("123456789000");
        conta.setDataAtualizacao(LocalDateTime.of(2019, 01, 01, 12, 12, 12));
        conta.setDataCriacao(LocalDateTime.of(2019, 01, 01, 12, 12, 12));
        return conta;
    }

    public static ContaRequest createContaRequest() {
        ContaRequest conta = new ContaRequest();
        conta.setAgencia("1234");
        conta.setNumero("123456");
        conta.setCpf("23456789000");
        return conta;
    }

    public static PageOut<Conta> createContaPage() {
        PageOut<Conta> pageConta = new PageOut<>();
        pageConta.setTotalPages(1);
        pageConta.setTotalElements(10L);
        pageConta.setElements(Arrays.asList(create()));
        return pageConta;
    }
}
