package br.com.ged.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.ged.domain.Pessoa;
import br.com.ged.util.DateUtil;
import br.com.ged.util.MaskUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PessoaDTO {

	private Long id;

	private String nome;

	private String cpf;

	private String logradouro;

	private String dataCriacao;

	private List<DocumentoDTO> documentos;

	public Pessoa toPessoa() {
		var pessoa = new Pessoa();

		pessoa.setId(this.id);
		pessoa.setNome(this.nome);
		pessoa.setLogradouro(this.logradouro);
		pessoa.setDataCriacao(LocalDate.now());

		return pessoa;
	}

	public static PessoaDTO fromPessoa(Pessoa entity) {
		var dto = new PessoaDTO();
		dto.setNome(entity.getNome());
		dto.setCpf(formatCpfCnpj(entity.getCpfCnpj()));
		dto.setLogradouro(entity.getLogradouro());
		dto.setDataCriacao(DateUtil.convertLocalDateToString(entity.getDataCriacao(), "dd/MM/yyyy"));
		if(Objects.nonNull(entity.getDocumentos())) {
			dto.setDocumentos(entity.getDocumentos().stream()
					.map(DocumentoDTO::fromDocumento)
					.collect(Collectors.toList()));
		}
		return dto;
	}

	private static String formatCpfCnpj(String cpfCnpj) {
		if(Objects.nonNull(cpfCnpj)) {
			if(cpfCnpj.length() == 11) {
				return MaskUtil.includeMask(cpfCnpj, "###.###.###-##");
			} else if(cpfCnpj.length() == 14) {
				return MaskUtil.includeMask(cpfCnpj, "##.###.###/####-##");
			} else if(cpfCnpj.length() == 9) {
				return MaskUtil.includeMask(cpfCnpj, "###.###.###");
			} else {
				return cpfCnpj;
			}
		} else {
			return "";
		}
	}

	@Override
	public String toString() {
		return "PessoaDTO [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", logradouro=" + logradouro
				+ ", dataCriacao=" + dataCriacao + ", documentos=" + documentos + "]";
	}
}
