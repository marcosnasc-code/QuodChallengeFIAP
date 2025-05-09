package br.com.fiap.QuodChallenge.DTO;

import br.com.fiap.QuodChallenge.models.TipoBiometriaEnum;

public record ValidacaoDTO(
         String transacaoId,
         TipoBiometriaEnum tipoBiometria,
         String fabricante,
         String modelo,
         String sistemaOperacional
) {

    public String getTransacaoId() {
        return transacaoId;
    }

    public TipoBiometriaEnum getTipoBiometria() {
        return tipoBiometria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }
}

