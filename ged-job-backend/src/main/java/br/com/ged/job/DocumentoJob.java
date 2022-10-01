package br.com.ged.job;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.ged.service.DocumentoService;
import io.quarkus.scheduler.Scheduled;

@ApplicationScoped
public class DocumentoJob {

	@Inject
	DocumentoService service;


	void createDirectory() {
		service.criarDiretorio();
	}

//	@Scheduled(cron = "{cron.expr}")
	@Scheduled(every="{cron.seg}")
	void saveDocumento() {
		service.lerDiretorio();
	}
}
