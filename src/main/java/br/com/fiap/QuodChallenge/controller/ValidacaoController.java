package br.com.fiap.QuodChallenge.controller;

import br.com.fiap.QuodChallenge.DTO.ValidacaoDTO;
import br.com.fiap.QuodChallenge.models.NotificacaoModel;
import br.com.fiap.QuodChallenge.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/validacao")
public class ValidacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping(
            value = "/facial",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validacaoFacial(
            @RequestPart("imagem") MultipartFile imagem,
            @RequestPart("dados") ValidacaoDTO validacaoDTO) {
            
        try {
            NotificacaoModel resposta = notificacaoService.validarFacial(imagem, validacaoDTO);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar facial: " + e.getMessage());
        }
    }

    @PostMapping(
            value = "/documento",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validacaoDocumento(
            @RequestPart("imagem") MultipartFile imagem,
            @RequestPart("dados") ValidacaoDTO validacaoDTO) {

        try {
            NotificacaoModel resposta = notificacaoService.validarDocumento(imagem, validacaoDTO);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar documento: " + e.getMessage());
        }
    }

    @PostMapping(
            value = "/digital",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validacaoDigital(
            @RequestPart("imagem") MultipartFile imagem,
            @RequestPart("dados") ValidacaoDTO validacaoDTO) {

        try {
            NotificacaoModel resposta = notificacaoService.validarDigital(imagem, validacaoDTO);
            return ResponseEntity.ok(resposta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao processar biometria digital: " + e.getMessage());
        }
    }
}