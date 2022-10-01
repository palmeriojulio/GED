package br.com.ged.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.ged.service.DocumentoService;

@Path("/documento")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Documento", description = "Endpoints relacionado aos documentos que serão salvos")
public class DocumentoResource {

	@Inject
	DocumentoService service;

	@GET
	@Operation(summary = "Listar todos os documentos salvos")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Documentos listados com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "500", description = "Erro ao listar documentos",
			content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	public Response listarDocumentos() {
		try {
			return Response.status(Status.OK).entity(service.getAll()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@GET
	@Path("gerarDocumentoVisualizar")
	@Operation(summary = "Obter documento salvo no archive")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Documento gerado com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "500", description = "Erro ao gerar documento",
			content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	public Response gerarDocumentoVisualizar(
			@QueryParam("nomeArquivo") final String nomeArquivo) {
		try {
			return Response.status(Status.OK).entity(service.gerarDocumentoVisualizar(nomeArquivo)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}


}
