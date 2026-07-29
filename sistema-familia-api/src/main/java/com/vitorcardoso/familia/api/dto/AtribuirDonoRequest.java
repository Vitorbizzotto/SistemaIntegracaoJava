package com.vitorcardoso.familia.api.dto;

import jakarta.validation.constraints.NotNull;

public class AtribuirDonoRequest {

    @NotNull(message = "O id do novo dono e obrigatorio.")
    private Long novoDonoId;

    public Long getNovoDonoId() {
        return novoDonoId;
    }

    public void setNovoDonoId(Long novoDonoId) {
        this.novoDonoId = novoDonoId;
    }
}
