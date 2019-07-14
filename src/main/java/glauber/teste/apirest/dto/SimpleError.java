package glauber.teste.apirest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

public class SimpleError {

    private String message;
    private String codigo;
    private Object objeto;
    private List<SimpleError> details = new ArrayList<>();

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Object[] mensagemArgs;

    public SimpleError() {
    }

    public SimpleError(String message, String codigo, Object[] mensagemArgs) {
        this.message = message;
        this.codigo = codigo;
        this.mensagemArgs = mensagemArgs;
    }

    public SimpleError(String mensagem, Object... args) {
        this.message = mensagem;
        this.mensagemArgs = args;
    }

    public void addDetail(SimpleError error) {
        this.details.add(error);
    }

    public void addDetail(String detailMessage) {
        addDetail(new SimpleError(detailMessage));
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Object getObjeto() {
        return objeto;
    }

    public void setObjeto(Object objeto) {
        this.objeto = objeto;
    }

    public List<SimpleError> getDetails() {
        return details;
    }

    public void setDetails(List<SimpleError> details) {
        this.details = details;
    }

    public void addDetalhe(SimpleError detailError) {
        if (details == null) {
            details = new ArrayList<>();
        }
        details.add(detailError);
    }

    public void addDetalhe(String detailMessage, Object... detailMessageArgs) {
        addDetalhe(new SimpleError(detailMessage, detailMessageArgs));
    }

    public Object[] getMensagemArgs() {
        return (mensagemArgs == null || mensagemArgs.length == 0) ? null : mensagemArgs;
    }
}