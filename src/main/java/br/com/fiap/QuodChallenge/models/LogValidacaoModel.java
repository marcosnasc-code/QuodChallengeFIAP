package br.com.fiap.QuodChallenge.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logs_validacao")
public class LogValidacaoModel {

    @Id
    private String id;
    private String transacaoId;
    private TipoBiometriaEnum tipoBiometria;
    private TipoFraudeEnum tipoFraude;
    private LocalDateTime dataValidacao;
    private boolean aprovado;
    private Map<String, String> dispositivo;
    private Map<String, Object> metadados;
    private String canalNotificacao;
    private String notificadoPor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransacaoId() {
        return transacaoId;
    }

    public void setTransacaoId(String transacaoId) {
        this.transacaoId = transacaoId;
    }

    public TipoBiometriaEnum getTipoBiometria() {
        return tipoBiometria;
    }

    public void setTipoBiometria(TipoBiometriaEnum tipoBiometria) {
        this.tipoBiometria = tipoBiometria;
    }

    public TipoFraudeEnum getTipoFraude() {
        return tipoFraude;
    }

    public void setTipoFraude(TipoFraudeEnum tipoFraude) {
        this.tipoFraude = tipoFraude;
    }

    public LocalDateTime getDataValidacao() {
        return dataValidacao;
    }

    public void setDataValidacao(LocalDateTime dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    public Map<String, String> getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Map<String, String> dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Map<String, Object> getMetadados() {
        return metadados;
    }

    public void setMetadados(Map<String, Object> metadados) {
        this.metadados = metadados;
    }

    public String getCanalNotificacao() {
        return canalNotificacao;
    }

    public void setCanalNotificacao(String canalNotificacao) {
        this.canalNotificacao = canalNotificacao;
    }

    public String getNotificadoPor() {
        return notificadoPor;
    }

    public void setNotificadoPor(String notificadoPor) {
        this.notificadoPor = notificadoPor;
    }
} 