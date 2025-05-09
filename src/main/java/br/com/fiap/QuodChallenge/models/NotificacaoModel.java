package br.com.fiap.QuodChallenge.models;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "validacoes")
public class NotificacaoModel {

    @Id
    private String transacaoId;

    private TipoBiometriaEnum tipoBiometria;
    private TipoFraudeEnum tipoFraude;
    private LocalDate dataCaptura;
    private CanalNotificacaoEnum canalNotificacao;
    private String notificadoPor = ("");
    private Map<String, String> dispositivo;
    private Map<String, Object> metaDados;

    public String getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }

    public Map<String, Object> getMetaDados() {
        return metaDados;
    }

    public void setMetaDados(Map<String, Object> metaDados) {
        this.metaDados = metaDados;
    }

    public Map<String, String> getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Map<String, String> dispositivo) {
        this.dispositivo = dispositivo;
    }

    public CanalNotificacaoEnum getCanalNotificacao() {
        return canalNotificacao;
    }

    public void setCanalNotificacao(CanalNotificacaoEnum canalNotificacao) {
        this.canalNotificacao = canalNotificacao;
    }

    public LocalDate getDataCaptura() {
        return dataCaptura;
    }

    public void setDataCaptura(LocalDate dataCaptura) {
        this.dataCaptura = dataCaptura;
    }

    public TipoFraudeEnum getTipoFraude() {
        return tipoFraude;
    }

    public String getNotificadoPor() {
        return notificadoPor;
    }

    public void setNotificadoPor(String notificadoPor) {
        this.notificadoPor = notificadoPor;
    }

    public void setTipoFraude(TipoFraudeEnum tipoFraude) {
        this.tipoFraude = tipoFraude;
    }

    public TipoBiometriaEnum getTipoBiometria() {
        return tipoBiometria;
    }

    public void setTipoBiometria(TipoBiometriaEnum tipoBiometria) {
        this.tipoBiometria = tipoBiometria;
    }

}






