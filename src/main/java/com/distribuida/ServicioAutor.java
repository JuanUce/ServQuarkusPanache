package com.distribuida;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
@Path("/autores")
public class ServicioAutor {

	@GET
	public List<Autor> listarAutores() {
		System.out.println("Select Autores");
		return Autor.listAll();
	}

	@POST
	@Transactional
	public Response crearAutor(Autor aut) {
		System.out.println("Crear un Autor");
		if (aut.getNombre() != null) {
			Autor.persist(aut);
			return Response.ok(aut).status(201).build();
		} else {

			return Response.status(Response.Status.NOT_FOUND).entity("No se envió un autor").build();
		}
	}

	@GET
	@Path("/{id}")
	public Autor AutorgetById(@PathParam(value = "id") Long id) {
		System.out.println("Seleccionar por Autor");
		Autor aut = Autor.findById(id);
		if (aut == null) {
			throw new WebApplicationException("Autor con id=" + id + " No existe", 404);
		}
		return aut;
	}

	@DELETE
	@Path("/{id}")
	@Transactional
	public Response deleteAutor(@PathParam(value = "id") Long id) {
		System.out.println("Eliminar un Autor");
		Autor aut = Autor.findById(id);
		if (aut == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("No se pudo eliminar Autor").build();
		} else {
			aut.delete();
			return Response.ok(aut).status(201).build();

		}
	}
	@PUT
	@Transactional
	public Autor updateActor(Autor au){	
		System.out.println("Actualizar un Autor");
	       if (au.getId()== null) {
	            throw new WebApplicationException("Cliente no en enviado", 422);
	        }
	       Autor entity = Autor.findById(au.getId());
	        if (entity == null) {
	            throw new WebApplicationException("Autor con id " + au.getId() + " no existe", 404);
	        }
	        entity.setId(au.getId());
	        entity.setEdad(au.getEdad());
	        entity.setGenero(au.getGenero());
	        entity.setNombre(au.getNombre());
	        return entity;			
	}
}
