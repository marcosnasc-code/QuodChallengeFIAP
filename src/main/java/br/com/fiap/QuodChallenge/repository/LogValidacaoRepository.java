package br.com.fiap.QuodChallenge.repository;

import br.com.fiap.QuodChallenge.models.LogValidacaoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogValidacaoRepository extends MongoRepository<LogValidacaoModel, String> {
    
    @Query("{ 'aprovado': true }")
    List<LogValidacaoModel> findAllAprovados();
    
    @Query("{ 'aprovado': false }")
    List<LogValidacaoModel> findAllReprovados();
    
    List<LogValidacaoModel> findByTransacaoId(String transacaoId);
    
    List<LogValidacaoModel> findByTipoBiometria(String tipoBiometria);
} 