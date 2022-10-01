package br.com.ged.resources;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import br.com.ged.service.PessoaService;

@Path("/pessoa")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Pessoa", description = "Endpoints relacionado as pessoas")
public class PessoaResource {

	@Inject
	PessoaService service;

	@GET
	@Operation(summary = "Listar todas as pessoas salvos")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pessoas listadas com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "500", description = "Erro ao listar pessoas",
			content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	public Response listarPessoas() {
		try {
			return Response.status(Status.OK).entity(service.getAll()).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@GET
	@Path("findPessoaByCpf/{cpf}")
	@Operation(summary = "Buscar pessoa por cpf")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pessoas listadas com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "500", description = "Erro ao listar pessoas",
			content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	public Response findPessoaByCpf(
			@PathParam("cpf") final String cpf) {
		try {
			return Response.status(Status.OK).entity(service.findPessoaByCpf(cpf)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@GET
	@Path("listPessoaByNome/{nome}")
	@Operation(summary = "Buscar pessoas por nome")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pessoas listadas com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "500", description = "Erro ao listar pessoas",
			content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	public Response listPessoaByNome(
			@PathParam("nome") final String nome) {
		try {
			return Response.status(Status.OK).entity(service.listPessoaByNome(nome)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@GET
	@Path("listPessoaByEndereco/{endereco}")
	@Operation(summary = "Buscar pessoas por nome")
	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pessoas listadas com sucesso",
					content = @Content(mediaType = MediaType.APPLICATION_JSON)),
			@APIResponse(responseCode = "500", description = "Erro ao listar pessoas",
			content = @Content(mediaType = MediaType.APPLICATION_JSON))
	})
	public Response listPessoaByEndereco(
			@PathParam("endereco") final String endereco) {
		try {
			return Response.status(Status.OK).entity(service.listPessoaByEndereco(endereco)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}
}
