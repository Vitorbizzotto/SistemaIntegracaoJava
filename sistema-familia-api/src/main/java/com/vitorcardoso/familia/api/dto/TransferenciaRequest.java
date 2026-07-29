package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.NotNull;

public class TransferenciaRequest {

    @NotNull(message = "O id do novo dono e obrigatorio.")
    private Long novoDonoId;

    private boolean aceitaEmprestimo;

    public Long getNovoDonoId() {
        return novoDonoId;
    }

    public void setNovoDonoId(Long novoDonoId) {
        this.novoDonoId = novoDonoId;
    }

    public boolean isAceitaEmprestimo() {
        return aceitaEmprestimo;
    }

    public void setAceitaEmprestimo(boolean aceitaEmprestimo) {
        this.aceitaEmprestimo = aceitaEmprestimo;
    }
}
