package bh.ws.example.rest;

import bh.ws.example.domain.Person;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/person")
public class ExampleRestService implements Serializable {

	@Inject
	EntityManager em;
	
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



	@GET
    @Path("fromDB")
	@Produces({MediaType.APPLICATION_JSON})
    public Response getPersonFromDB(){
		Query query = em.createNativeQuery("select * from BR_PERSON");
		List<Object[]> resultList = query.getResultList();
		for(int i=0; i< resultList.size(); i++){
			Object[] person = resultList.get(i);
			System.out.println("Person "+(i+1)+ " : ");
			for(int p=0; p<person.length; p++){
				System.out.println("[ "+ person[p] + " ]");
			}
		}
		return Response.ok("").build();
    }

}
