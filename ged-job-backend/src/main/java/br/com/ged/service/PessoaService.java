package br.com.ged.service;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import br.com.ged.dao.PessoaRepository;
import br.com.ged.domain.Pessoa;
import br.com.ged.dto.PessoaDTO;
import br.com.ged.util.Util;

@ApplicationScoped
public class PessoaService {

	@Inject
	PessoaRepository repository;

	@Inject
	DocumentoService documentoService;

	public List<PessoaDTO> getAll() {
		return repository.listAll().stream()
				.map(PessoaDTO::fromPessoa)
				.collect(Collectors.toList());
	}

	public PessoaDTO findPessoaByCpf(String cpf) {
		var pessoa = repository.findPessoaByCpf(cpf.replaceAll("[^0-9]", ""));
		if(pessoa.isPresent()) {
			return PessoaDTO.fromPessoa(pessoa.get());
		} else {
			return null;
		}
	}

	public List<PessoaDTO> listPessoaByNome(String nome) {
		return repository.listPessoaByNome(nome.toUpperCase()).stream()
				.map(PessoaDTO::fromPessoa)
				.collect(Collectors.toList());
	}

	public List<PessoaDTO> listPessoaByEndereco(String endereco) {
		return repository.listPessoaByEndereco(endereco.toUpperCase()).stream()
				.map(PessoaDTO::fromPessoa)
				.collect(Collectors.toList());
	}

	@Transactional(rollbackOn = RuntimeException.class)
	public void save(Pessoa pessoa) {
		pessoa.persist();
	}

	public Pessoa prepararObjeto(File origem, File destino) {
		String[] detalhes = Util.identificarDocumentos(origem.getName());
		if(Objects.nonNull(detalhes)) {
			if(detalhes[0].matches("[+-]?\\d*(\\.\\d+)?")) {
				return verificarCadastroPessoa(origem, destino, detalhes);
			} else {
				return verificarCadastroPessoaSemCPF(origem, destino, detalhes);
			}
		} else {
			return null;
		}
	}

	private Pessoa verificarCadastroPessoaSemCPF(File origem, File destino, String[] detalhes) {
		var pessoaCadastro = repository.findPessoaByNomeAndNotCpfCnpj(detalhes[0].replaceAll("(?i).pdf", "").toUpperCase().toString());
		if(!pessoaCadastro.isPresent()) {
			return condicionalCadastro(detalhes);
		} else {
			return pessoaCadastro.get();
		}
	}

	private Pessoa verificarCadastroPessoa(File origem, File destino, String[] detalhes) {
		var pessoaCadastro = repository.findPessoaByCpf(detalhes[0].toString());
		if(!pessoaCadastro.isPresent()) {
			return condicionalCadastro(detalhes);
		} else {
			return pessoaCadastro.get();
		}
	}

	private Pessoa condicionalCadastro(String[] detalhes) {
		var pessoa = new Pessoa();

		pessoa.setDataCriacao(LocalDate.now());
		if(detalhes.length == 1) {
			pessoa.setNome(detalhes[0].replaceAll("(?i).pdf", "").toUpperCase().toString());
		} else if(detalhes.length == 2) {
			pessoa.setCpfCnpj(detalhes[0].toString());
			pessoa.setNome(detalhes[1].replaceAll("(?i).pdf", "").toUpperCase().toString());
		} else if (detalhes.length == 3 || detalhes.length == 4) {
			pessoa.setCpfCnpj(detalhes[0].toString());
			pessoa.setNome(detalhes[1].toUpperCase().toString());
			pessoa.setLogradouro(detalhes[2].replaceAll("(?i).pdf", "").toUpperCase().toString());
		}

		return pessoa;
	}
}
