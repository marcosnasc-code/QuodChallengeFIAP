package br.com.fiap.QuodChallenge.controller;

import br.com.fiap.QuodChallenge.models.LogValidacaoModel;
import br.com.fiap.QuodChallenge.repository.LogValidacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private LogValidacaoRepository logValidacaoRepository;

    @PostMapping("/fraude")
    public ResponseEntity<?> registrarFraude(@RequestBody Map<String, Object> body) {
        // Salvar log de notificação de fraude
        LogValidacaoModel log = new LogValidacaoModel();
        log.setTransacaoId((String) body.getOrDefault("transacaoId", ""));
        log.setTipoBiometria(body.get("tipoBiometria") != null ? br.com.fiap.QuodChallenge.models.TipoBiometriaEnum.valueOf(body.get("tipoBiometria").toString()) : null);
        log.setTipoFraude(body.get("tipoFraude") != null ? br.com.fiap.QuodChallenge.models.TipoFraudeEnum.valueOf(body.get("tipoFraude").toString()) : null);
        log.setDataValidacao(LocalDateTime.now());
        log.setAprovado(false); // Sempre negativo pois é notificação de fraude
        log.setDispositivo((Map<String, String>) body.getOrDefault("dispositivo", null));
        log.setMetadados((Map<String, Object>) body.getOrDefault("metadados", null));
        log.setCanalNotificacao(body.get("canalNotificacao") != null ? body.get("canalNotificacao").toString() : null);
        log.setNotificadoPor(body.get("notificadoPor") != null ? body.get("notificadoPor").toString() : null);
        logValidacaoRepository.save(log);
        return ResponseEntity.ok("Notificação de fraude recebida e logada!");
    }
} 