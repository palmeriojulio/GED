package br.com.ged.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
@Getter @Setter
public class Pessoa extends PanacheEntityBase {

	@Id
	@SequenceGenerator(name="pessoa_id_seq", sequenceName="pessoa_id_seq", allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_id_seq")
	private Long id;

	@Column(name = "nome")
	private String nome;

	@Column(name = "cpf_cnpj")
	private String cpfCnpj;

	@Column(name = "logradouro")
	private String logradouro;

	@Column(name = "data_criacao")
	private LocalDate dataCriacao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa", cascade = CascadeType.ALL)
	private List<Documento> documentos;
}
