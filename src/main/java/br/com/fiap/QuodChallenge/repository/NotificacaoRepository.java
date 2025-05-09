package br.com.fiap.QuodChallenge.repository;

import br.com.fiap.QuodChallenge.models.NotificacaoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotificacaoRepository extends MongoRepository<NotificacaoModel, String> {
}
