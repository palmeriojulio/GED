package br.com.ged.dto;

import java.time.LocalDate;

import br.com.ged.domain.Documento;
import br.com.ged.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DocumentoDTO {

	private Long id;

	private String nome;

	private String caminhoArchive;

	private String dataCriacao;

	private PessoaDTO pessoa;

	private byte[] dados;

	public Documento toDocumento() {
		var documento = new Documento();

		documento.setId(this.id);
		documento.setNome(this.nome);
		documento.setCaminhoArchive(this.caminhoArchive);
		documento.setDataCriacao(LocalDate.now());
		documento.setPessoa(this.pessoa.toPessoa());
		return documento;
	}

	public static DocumentoDTO fromDocumento(Documento entity) {
		var dto = new DocumentoDTO();

		dto.setId(entity.getId());
		dto.setNome(entity.getNome());
		dto.setCaminhoArchive(entity.getCaminhoArchive());
		dto.setDataCriacao(DateUtil.convertLocalDateToString(entity.getDataCriacao(), "dd/MM/yyyy"));

		return dto;
	}

	@Override
	public String toString() {
		return "DocumentoDTO [id=" + id + ", caminhoArchive=" + caminhoArchive + ", dataCriacao=" + dataCriacao
				+ ", pessoa=" + pessoa + "]";
	}

}
