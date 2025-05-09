package br.com.fiap.QuodChallenge.controller;

import br.com.fiap.QuodChallenge.models.LogValidacaoModel;
import br.com.fiap.QuodChallenge.repository.LogValidacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/logs")
public class LogValidacaoController {

    @Autowired
    private LogValidacaoRepository logValidacaoRepository;

    @GetMapping("/aprovados")
    public ResponseEntity<List<LogValidacaoModel>> getLogsAprovados() {
        List<LogValidacaoModel> logs = logValidacaoRepository.findAllAprovados();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/reprovados")
    public ResponseEntity<List<LogValidacaoModel>> getLogsReprovados() {
        List<LogValidacaoModel> logs = logValidacaoRepository.findAllReprovados();
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/transacao/{transacaoId}")
    public ResponseEntity<List<LogValidacaoModel>> getLogsPorTransacao(@PathVariable String transacaoId) {
        List<LogValidacaoModel> logs = logValidacaoRepository.findByTransacaoId(transacaoId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/biometria/{tipoBiometria}")
    public ResponseEntity<List<LogValidacaoModel>> getLogsPorTipoBiometria(@PathVariable String tipoBiometria) {
        List<LogValidacaoModel> logs = logValidacaoRepository.findByTipoBiometria(tipoBiometria);
        return ResponseEntity.ok(logs);
    }
} 