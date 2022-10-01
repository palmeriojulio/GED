package br.com.ged.dao;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import br.com.ged.domain.Pessoa;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class PessoaRepository implements PanacheRepository<Pessoa> {

	@Override
	public List<Pessoa> listAll() {
		try {
			return list("order by nome ASC");
		} catch (Exception e) {
			return null;
		}
	}

	public Optional<Pessoa> findPessoaByCpf(String cpf) {
		try {
			return find("cpfCnpj =: cpf",
					Parameters.with("cpf", cpf)).firstResultOptional();
		} catch (RuntimeException e) {
			return Optional.empty();
		}
	}

	public Optional<Pessoa> findPessoaByNome(String nome) {
		try {
			return find("nome =: nome",
					Parameters.with("nome", nome)).firstResultOptional();
		} catch (RuntimeException e) {
			return Optional.empty();
		}
	}

	public Optional<Pessoa> findPessoaByNomeAndNotCpfCnpj(String nome) {
		try {
			return find("nome =: nome and cpfCnpj is null",
					Parameters.with("nome", nome)).firstResultOptional();
		} catch (RuntimeException e) {
			return Optional.empty();
		}
	}

	public List<Pessoa> listPessoaByNome(String nome) {
		try {
			return find("nome like CONCAT('%', ?1, '%') order by nome ASC ", nome).list();
		} catch (RuntimeException e) {
			return null;
		}
	}

	public List<Pessoa> listPessoaByEndereco(String endereco) {
		try {
			return find("logradouro like CONCAT('%', ?1, '%') order by logradouro ASC ", endereco).list();
		} catch (RuntimeException e) {
			return null;
		}
	}
}
