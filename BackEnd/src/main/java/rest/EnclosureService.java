package rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import enclosure.Enclosure;
import rest.interfacemanager.IEnclosureManager;

@Path("/enclosures")
public class EnclosureService {

	@Inject
	IEnclosureManager imanager;
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Enclosure> getEnclosuresList() {
		
		return imanager.getAll();
	}
	
	@GET
	@Path("/{name:[a-zA-Z]+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Enclosure> getEnclosuresByName(@PathParam("name") String subname) {
		
		return imanager.getByName(subname);
	}
	
	@GET
	@Path("/{id:[0-9]+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Enclosure getEnclosureById(@PathParam("id") int id) {
		
		return imanager.get(id);
	}
	
	@PUT
	@Path("/{id:[0-9]+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response modifyEnclosureById(Enclosure enclosure, @PathParam("id") int id) {
		
		if (id != enclosure.getId()) {
			return Response.status(Status.CONFLICT).build();
		}
		
		WebServiceResponse response =  imanager.modify(enclosure);
		
		if (response.equals(WebServiceResponse.REQUESTED_RANGE_NOT_SATISFIABLE)) {
			return Response.status(Status.REQUESTED_RANGE_NOT_SATISFIABLE).build();
		}
		
		if (response.equals(WebServiceResponse.PRECONDITION_FAILED)) {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}
	
	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteEnclosureById(@PathParam("id") int id) {
		
		WebServiceResponse response =  imanager.delete(id);
		
		if (response.equals(WebServiceResponse.REQUESTED_RANGE_NOT_SATISFIABLE)) {
			return Response.status(Status.REQUESTED_RANGE_NOT_SATISFIABLE).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}
	
	@POST
	@Path("/new")
	public Response addEnclosure(Enclosure enclosure) {
		
		WebServiceResponse response =  imanager.add(enclosure);
		if (response.equals(WebServiceResponse.UNAUTHORIZED)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		if (response.equals(WebServiceResponse.PRECONDITION_FAILED)) {
			return Response.status(Status.PRECONDITION_FAILED).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}
}
