package br.com.ged.dao;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import br.com.ged.domain.Documento;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class DocumentoRepository implements PanacheRepository<Documento> {

	public Optional<Documento> findDocumentoByNomeAndPessoa(String nomeDocumento, Long idPessoa) {
		try {
			return find("nome =: nomeDocumento and pessoa.id =: idPessoa",
					Parameters.with("nomeDocumento", nomeDocumento)
					.and("idPessoa", idPessoa)).firstResultOptional();
		} catch (RuntimeException e) {
			return Optional.empty();
		}
	}
}
