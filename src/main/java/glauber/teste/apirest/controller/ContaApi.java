package glauber.teste.apirest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import glauber.teste.apirest.controller.request.ContaRequest;
import glauber.teste.apirest.controller.response.ContaResponse;
import glauber.teste.apirest.dto.Conta;
import glauber.teste.apirest.dto.PageOut;
import glauber.teste.apirest.service.ContaService;
import glauber.teste.apirest.utils.HttpUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/conta")
public class ContaApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContaApi.class);
    private ContaService contaService;
    private ObjectMapper objectMapper;

    public ContaApi(ContaService contaService, ObjectMapper objectMapper) {
        this.contaService = contaService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    @ApiOperation(value = "Cria uma conta nova.")
    @ApiResponses(@ApiResponse(code = HttpUtil.CREATED, message = HttpUtil.CREATED_MESSAGE))
    public ResponseEntity postConta(@Valid @RequestBody ContaRequest contaRequest) {
        LOGGER.info("Iniciado criação de conta.");
        Conta conta = objectMapper.convertValue(contaRequest, Conta.class);
        contaService.createConta(conta);
        LOGGER.info("Finalizado criação de conta.");
        return ResponseEntity.ok(Void.class);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza dados de uma conta")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.OK_MESSAGE))
    public ResponseEntity putConta(@PathVariable("id") Long id,
                                   @Valid @RequestBody ContaRequest contaRequest) {
        LOGGER.info("Iniciado atualização de conta.");
        Conta conta = objectMapper.convertValue(contaRequest, Conta.class);
        contaService.updateConta(id, conta);
        LOGGER.info("Finalizado atualização de conta.");
        return ResponseEntity.ok(Void.class);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta uma conta.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.OK_MESSAGE))
    public ResponseEntity deleteConta(@PathVariable("id") Long id) {
        LOGGER.info("Iniciado delete de conta.");
        contaService.deleteConta(id);
        LOGGER.info("Finalizado delete de conta.");
        return ResponseEntity.ok(Void.class);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca uma conta através do id.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.OK_MESSAGE))
    public ResponseEntity<ContaResponse> getConta(@PathVariable("id") Long id) {
        LOGGER.info("Iniciado busca de conta por id.");
        Conta conta = contaService.getConta(id);
        ContaResponse contaResponse = objectMapper.convertValue(conta, ContaResponse.class);
        LOGGER.info("Finalizado busca de conta por id");
        return ResponseEntity.ok(contaResponse);
    }

    @GetMapping("")
    @ApiOperation(value = "Busca lista de conta paginada.")
    @ApiResponses(@ApiResponse(code = HttpUtil.OK, message = HttpUtil.OK_MESSAGE))
    public ResponseEntity<PageOut<ContaResponse>> getContaPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        LOGGER.info("Iniciado busca lista de conta paginada");
        PageRequest pageable = PageRequest.of(page - 1, size);
        PageOut<Conta> pageConta = contaService.getPageConta(pageable);
        PageOut<ContaResponse> contaResponsePageOut = parsecontaPagin(pageConta);
        LOGGER.info("Finalizado busca lista de conta paginada");
        return ResponseEntity.ok(contaResponsePageOut);
    }

    private PageOut<ContaResponse> parsecontaPagin(PageOut<Conta> pageConta) {
        PageOut<ContaResponse> contaResponse = new PageOut<>();
        contaResponse.setTotalElements(pageConta.getTotalElements());
        contaResponse.setTotalPages(pageConta.getTotalPages());
        List<ContaResponse> listConta = pageConta.getElements().stream()
                .map(it -> objectMapper.convertValue(it, ContaResponse.class))
                .collect(Collectors.toList());
        contaResponse.setElements(listConta);
        return contaResponse;
    }
}
