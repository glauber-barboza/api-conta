package glauber.teste.apirest.controller.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContaRequest {
    @ApiModelProperty(value = "Informe o número da conta ex: (123456).")
    @NotNull(message = "conta.numeroIsEmpty")
    @Size(min = 6, max = 6, message = "conta.numeroInvalidSize")
    @Pattern(regexp = "[0-9]*", message = "conta.numeroInvalidCharecter")
    private String numero;

    @ApiModelProperty(value = "Informe a agencia da conta ex: (1234).")
    @NotNull(message = "conta.agenciaIsEmpty")
    @Size(min = 4, max = 4, message = "conta.agenciaSize")
    @Pattern(regexp = "[0-9]*", message = "conta.agenciaInvalidCharecter")
    private String agencia;

    @ApiModelProperty(value = "Informe um cpf da conta ex: (123456789011).")
    @NotNull(message = "conta.cpfIsEmpty")
    @Size(min = 11, max = 11, message = "conta.cpfSize")
    @Pattern(regexp = "[0-9]*", message = "conta.cpfInvalidCharecter")
    private String cpf;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
