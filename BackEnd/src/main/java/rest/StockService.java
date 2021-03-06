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

import rest.interfacemanager.IStockManager;
import stock.Stock;

@Path("/stocks")
public class StockService {

	@Inject
	IStockManager imanager;
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Stock> getStocksList() {
		
		return imanager.getAll();
	}
	
	@GET
	@Path("/{id:[0-9]+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Stock getStockById(@PathParam("id") int id) {
		
		return imanager.get(id);
	}
	
	@PUT
	@Path("/{id:[0-9]+}")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response modifyStockById(Stock stock, @PathParam("id") int id) {
		
		if (id != stock.getId()) {
			return Response.status(Status.CONFLICT).build();
		}
		
		WebServiceResponse response =  imanager.modify(stock);
		
		if (response.equals(WebServiceResponse.REQUESTED_RANGE_NOT_SATISFIABLE)) {
			return Response.status(Status.REQUESTED_RANGE_NOT_SATISFIABLE).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}
	
	@DELETE
	@Path("/{id:[0-9]+}")
	public Response deleteStockById(@PathParam("id") int id) {
		
		WebServiceResponse response =  imanager.delete(id);
		
		if (response.equals(WebServiceResponse.REQUESTED_RANGE_NOT_SATISFIABLE)) {
			return Response.status(Status.REQUESTED_RANGE_NOT_SATISFIABLE).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}
	
	@POST
	@Path("/new")
	public Response addStock(Stock stock) {
		
		WebServiceResponse response =  imanager.add(stock);
		if (response.equals(WebServiceResponse.UNAUTHORIZED)) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		
		return Response.status(Status.ACCEPTED).build();
	}
}
