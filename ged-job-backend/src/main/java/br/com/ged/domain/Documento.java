package br.com.ged.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "documento")
@Getter @Setter
public class Documento extends PanacheEntityBase{

	@Id
	@SequenceGenerator(name="documento_id_seq", sequenceName="documento_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento_id_seq")
	private Long id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "caminho_archive", nullable = false)
	private String caminhoArchive;

	@Column(name = "data_criacao", nullable = false)
	private LocalDate dataCriacao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

	@Transient
	private byte[] dados;
}
