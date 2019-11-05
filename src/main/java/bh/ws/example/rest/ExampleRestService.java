package bh.ws.example.rest;

import bh.ws.example.domain.Person;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/person")
public class ExampleRestService implements Serializable {

	
	@GET
	@Path("all")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPersons() {
		Person p1 = new Person("Teo","123");
		Person p2 = new Person("Elias", "456");
		List<Person> personList = new ArrayList<>();
		personList.add(p1);
		personList.add(p2);
		return Response.ok(personList).build();
	}

	@GET
	@Path("withUsername")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPersonsWithUsername(@QueryParam("username") @NotNull String username) {
		Person p1 = new Person("Teo","123");
		return Response.ok(p1).build();
	}


}
