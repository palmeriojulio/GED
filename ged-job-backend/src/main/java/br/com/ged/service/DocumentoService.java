package br.com.ged.service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.com.ged.dao.DocumentoRepository;
import br.com.ged.domain.Documento;
import br.com.ged.domain.Pessoa;
import br.com.ged.dto.DocumentoDTO;
import br.com.ged.enums.DiretorioEnum;
import br.com.ged.util.Util;

@ApplicationScoped
public class DocumentoService {

	@Inject
	DocumentoRepository repository;

	@Inject
	PessoaService pessoaService;

	@ConfigProperty(name = "archive.origin")
	private String origin;

	public List<DocumentoDTO> getAll() {
		return repository.listAll().stream()
				.map(DocumentoDTO::fromDocumento)
				.collect(Collectors.toList());
	}

	public Documento findDocumentoByNomeAndPessoa(String nome, Long idPessoa) {
		var documento = repository.findDocumentoByNomeAndPessoa(nome, idPessoa);
		if(documento.isPresent()) {
			return documento.get();
		} else {
			return null;
		}
	}

	@Transactional(rollbackOn = RuntimeException.class)
	public void save(Documento doc) {
		doc.persist();
	}

	public DocumentoDTO gerarDocumentoVisualizar(String nomeArquivo) {
		var documento = new DocumentoDTO();
		String arquivo = Util.nomeArquivo(nomeArquivo, 1);
		try {
			String diretorio = Util.nomeArquivo(nomeArquivo, 2);
			File fileArchive = obterArquivo(arquivo, diretorio);

			if(Objects.nonNull(fileArchive)) {
				documento.setDados(Files.readAllBytes(fileArchive.toPath()));
				documento.setCaminhoArchive(fileArchive.getAbsolutePath());
				documento.setNome(fileArchive.getName());
				return documento;
			}
		} catch (RuntimeException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Documento verificarCadastroDocumento(File origem, File destino, Pessoa pessoaCadastro) {
		String[] detalhes = Util.identificarDocumentos(origem.getName());
		if(detalhes.length == 4) {
			var documentoSalvo = findDocumentoByNomeAndPessoa((detalhes.length == 4) ? detalhes[3].replace(".pdf", "").toUpperCase().toString() : "INDEFINIDO", pessoaCadastro.getId());
			if(Objects.isNull(documentoSalvo)) {
				return criarDocumento(origem, destino, pessoaCadastro, detalhes);
			} else {
				return null;
			}
		} else {
			var documentoSalvo = findDocumentoByNomeAndPessoa(origem.getName().toString(), pessoaCadastro.getId());
			if(Objects.isNull(documentoSalvo)) {
				return criarDocumento(origem, destino, pessoaCadastro, detalhes);
			} else {
				return null;
			}
		}
	}

	private Documento criarDocumento(File origem, File destino, Pessoa pessoaCadastro, String[] detalhes) {
		var documento = new Documento();
		documento.setNome((detalhes.length == 4) ? detalhes[3].replace(".pdf", "").toUpperCase().toString() : origem.getName().toString());
		documento.setCaminhoArchive(destino.getAbsolutePath());
		documento.setDataCriacao(LocalDate.now());
		documento.setPessoa(pessoaCadastro);
		return documento;
	}

	private File obterArquivo(String nomeArquivo, String diretorio) {
		try {
	        var dir = new File(origin+"\\"+diretorio);
			File[] matches = dir.listFiles(new FilenameFilter()
			{
				@Override
				public boolean accept(File dir, String name)
				{
					return name.startsWith(nomeArquivo) && name.endsWith(".pdf");
				}
			});

			for(File f : matches) {
				return f;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void criarDiretorio() {
		var dir = new File(origin);
        if(!dir.exists()) {
        	dir.mkdir();
        }
	}

	public void lerDiretorio() {
		try {

	        for (DiretorioEnum diretorio: DiretorioEnum.values()) {
	        	var dir = new File(origin+"/"+diretorio.name());
	            if(dir.exists()) {
	            	File[] matches = dir.listFiles(new FilenameFilter() {
	        			@Override
	        			public boolean accept(File dir, String name) {
	        				return name.endsWith(".pdf");
	        			}
	        		});

	                for(File f : matches) {
	                	var destino = origin+"\\"+diretorio.name()+"\\"+f.getName();
	                	var d = new File(destino);
	                	var pessoa = pessoaService.prepararObjeto(f, d);
	                	try {
	                		if(Objects.nonNull(pessoa)) {
	                    		if(Objects.isNull(pessoa.getId())) {
	                    			pessoaService.save(pessoa);
	                    		}

	                    		var documento = verificarCadastroDocumento(f, d, pessoa);
	                    		if(Objects.nonNull(documento)) {
	                    			save(documento);
	                    		}
//	            				mover(f, d);
	                    	}
	        			} catch (Exception e) {
	        				e.printStackTrace();
	        			}
	                }

	            } else {
	            	dir.mkdir();
	            }
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

//	private static void mover(File src, File dst) throws IOException {
//		try (InputStream in = new FileInputStream(src)) {
//			try (OutputStream out = new FileOutputStream(dst)) {
//				byte[] buf = new byte[1024];
//				int len;
//				while ((len = in.read(buf)) > 0) {
//					out.write(buf, 0, len);
//				}
//				src.delete();
//			}
//		} catch (RuntimeException e) {
//			e.printStackTrace();
//		}
//	}
}
