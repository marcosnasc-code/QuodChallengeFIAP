package br.com.fiap.QuodChallenge.service;

import br.com.fiap.QuodChallenge.DTO.ValidacaoDTO;
import br.com.fiap.QuodChallenge.models.CanalNotificacaoEnum;
import br.com.fiap.QuodChallenge.models.LogValidacaoModel;
import br.com.fiap.QuodChallenge.models.NotificacaoModel;
import br.com.fiap.QuodChallenge.models.TipoFraudeEnum;
import br.com.fiap.QuodChallenge.repository.LogValidacaoRepository;
import br.com.fiap.QuodChallenge.repository.NotificacaoRepository;
import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final LogValidacaoRepository logValidacaoRepository;
    private final Random random = new Random();
    private final RestTemplate restTemplate;

    public NotificacaoService(NotificacaoRepository repository, 
                            LogValidacaoRepository logValidacaoRepository,
                            RestTemplate restTemplate) {
        this.notificacaoRepository = repository;
        this.logValidacaoRepository = logValidacaoRepository;
        this.restTemplate = restTemplate;
    }

    public NotificacaoModel validarFacial(MultipartFile imagem, ValidacaoDTO validacaoDTO) throws Exception {
        return processarValidacao(imagem, validacaoDTO, TipoFraudeEnum.DEEPFAKE);
    }

    public NotificacaoModel validarDocumento(MultipartFile imagem, ValidacaoDTO validacaoDTO) throws Exception {
        return processarValidacao(imagem, validacaoDTO, TipoFraudeEnum.FOTO_DE_FOTO);
    }

    public NotificacaoModel validarDigital(MultipartFile imagem, ValidacaoDTO validacaoDTO) throws Exception {
        return processarValidacao(imagem, validacaoDTO, TipoFraudeEnum.MASCARA);
    }

    private NotificacaoModel processarValidacao(MultipartFile imagem, ValidacaoDTO validacaoDTO, TipoFraudeEnum tipoFraudePadrao) throws Exception {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("upload-", ".tmp");
            imagem.transferTo(tempFile);

            Metadata metadata = ImageMetadataReader.readMetadata(tempFile);
            ExifIFD0Directory exifDir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            GpsDirectory gpsDir = metadata.getFirstDirectoryOfType(GpsDirectory.class);

            String dataCaptura = exifDir != null && exifDir.containsTag(ExifIFD0Directory.TAG_DATETIME)
                    ? exifDir.getString(ExifIFD0Directory.TAG_DATETIME)
                    : LocalDate.now().toString();

            GeoLocation geo = gpsDir != null ? gpsDir.getGeoLocation() : null;

            int probabilidadeFraude = switch (validacaoDTO.getTipoBiometria()) {
                case FACIAL -> 20;
                case DIGITAL -> 15;
                case DOCUMENTO -> 25;
            };

            boolean fraude = random.nextInt(100) < probabilidadeFraude;
            TipoFraudeEnum tipoFraude = fraude ? tipoFraudePadrao : null;

            CanalNotificacaoEnum canalNotificacao = CanalNotificacaoEnum.values()[random.nextInt(CanalNotificacaoEnum.values().length)];

            Map<String, String> dispositivo = Map.of(
                    "fabricante", validacaoDTO.getFabricante(),
                    "modelo", validacaoDTO.getModelo(),
                    "sistemaOperacional", validacaoDTO.getSistemaOperacional()
            );

            Map<String, Object> metadados = new HashMap<>();
            if (geo != null) {
                metadados.put("latitude", geo.getLatitude());
                metadados.put("longitude", geo.getLongitude());
            }
            metadados.put("ipOrigem", "192.168.1.10");
            metadados.put("probabilidadeFraude", probabilidadeFraude);
            metadados.put("fraudeDetectada", fraude);

            NotificacaoModel validacao = new NotificacaoModel();
            validacao.setTransacaoId(validacaoDTO.getTransacaoId());
            validacao.setTipoBiometria(validacaoDTO.getTipoBiometria());
            validacao.setTipoFraude(tipoFraude);
            validacao.setDataCaptura(LocalDate.parse(dataCaptura));
            validacao.setDispositivo(dispositivo);
            validacao.setCanalNotificacao(canalNotificacao);
            validacao.setNotificadoPor("sistema-de-monitoramento");
            validacao.setMetaDados(metadados);

            notificacaoRepository.save(validacao);

            // Salvar log de validação
            LogValidacaoModel log = new LogValidacaoModel();
            log.setTransacaoId(validacaoDTO.getTransacaoId());
            log.setTipoBiometria(validacaoDTO.getTipoBiometria());
            log.setTipoFraude(tipoFraude);
            log.setDataValidacao(LocalDateTime.now());
            log.setAprovado(!fraude);
            log.setDispositivo(dispositivo);
            log.setMetadados(metadados);
            log.setCanalNotificacao(canalNotificacao.name());
            log.setNotificadoPor("sistema-de-monitoramento");
            
            logValidacaoRepository.save(log);

            if (fraude) {
                enviarNotificacaoFraude(validacao);
            }

            return validacao;

        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    private void enviarNotificacaoFraude(NotificacaoModel validacao) {
        String url = "http://localhost:8080/api/notificacoes/fraude";

        Map<String, Object> body = new HashMap<>();
        body.put("transacaoId", validacao.getTransacaoId());
        body.put("tipoBiometria", validacao.getTipoBiometria());
        body.put("tipoFraude", validacao.getTipoFraude());
        body.put("dataCaptura", validacao.getDataCaptura());
        body.put("dispositivo", validacao.getDispositivo());
        body.put("canalNotificacao", validacao.getCanalNotificacao());
        body.put("notificadoPor", validacao.getNotificadoPor());
        body.put("metadados", validacao.getMetaDados());

        restTemplate.postForObject(url, body, String.class);
    }
}
