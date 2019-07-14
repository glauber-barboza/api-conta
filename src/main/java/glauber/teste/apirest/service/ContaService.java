package glauber.teste.apirest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import glauber.teste.apirest.dto.Conta;
import glauber.teste.apirest.dto.PageOut;
import glauber.teste.apirest.entity.ContaEntity;
import glauber.teste.apirest.exception.ContaNotFoundException;
import glauber.teste.apirest.repository.ContaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    private ContaRepository contaRepository;
    private ObjectMapper objectMapper;

    public ContaService(ContaRepository contaRepository, ObjectMapper objectMapper) {
        this.contaRepository = contaRepository;
        this.objectMapper = objectMapper;
    }

    public void createConta(Conta conta) {
        ContaEntity contaEntity = objectMapper.convertValue(conta, ContaEntity.class);
        contaEntity.setStatus(true);
        contaEntity.setDataCriacao(LocalDateTime.now());
        contaRepository.save(contaEntity);
    }

    public void updateConta(Long id, Conta conta) {
        ContaEntity contaEntity = getContaEntity(id);
        dataModifyContaEntity(contaEntity, conta);
        contaRepository.save(contaEntity);
    }

    private void dataModifyContaEntity(ContaEntity contaEntity, Conta conta) {
        contaEntity.setCpf(conta.getCpf());
        contaEntity.setNumero(conta.getNumero());
        contaEntity.setAgencia(conta.getAgencia());
        contaEntity.setDataAtualizacao(LocalDateTime.now());
    }

    public void deleteConta(Long id) {
        ContaEntity contaEntity = getContaEntity(id);
        contaEntity.setStatus(false);
        contaEntity.setDataAtualizacao(LocalDateTime.now());
        contaRepository.save(contaEntity);
    }

    public Conta getConta(Long id) {
        ContaEntity contaEntity = getContaEntity(id);
        return objectMapper.convertValue(contaEntity, Conta.class);
    }

    public PageOut<Conta> getPageConta(Pageable pageable) {
        PageOut<Conta> pageOutConta = new PageOut<>();
        Page<ContaEntity> entityContaPage = contaRepository.findAll(pageable);

        pageOutConta.setTotalPages(entityContaPage.getTotalPages());
        pageOutConta.setTotalElements(entityContaPage.getTotalElements());
        List<Conta> listConta = entityContaPage.get()
                .map(it -> objectMapper.convertValue(it, Conta.class))
                .collect(Collectors.toList());
        pageOutConta.setElements(listConta);

        return pageOutConta;
    }

    private ContaEntity getContaEntity(Long id) {
        return contaRepository.findById(id).orElseThrow(() -> {
            throw new ContaNotFoundException();
        });
    }
}
