package bh.ws.example.rest;

import bh.ws.example.domain.PersonDTO;
import bh.ws.example.domain.SimplePerson;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Path("/person")
public class PersonService implements Serializable {

	@Inject
	EntityManager em;
	
	@GET
	@Path("all")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPersons() {
		SimplePerson p1 = new SimplePerson("Teo","123");
		SimplePerson p2 = new SimplePerson("Elias", "456");
		List<SimplePerson> simplePersonList = new ArrayList<>();
		simplePersonList.add(p1);
		simplePersonList.add(p2);
        GenericEntity<List<SimplePerson>> personGE =
                new GenericEntity<List<SimplePerson>>(simplePersonList){};
        return Response.ok(personGE).build();
	}

	@GET
	@Path("withUsername")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPersonsWithUsername(@QueryParam("username") @NotNull String username) {
		SimplePerson p1 = new SimplePerson("Teo","123");
		return Response.ok(p1).build();
	}


    @GET
    @Path("fromDB")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonFromDB(){
        Query query = em.createNativeQuery("select * from SB_REST_PERSON");
        List<Object[]> resultList = query.getResultList();
        for(int i=0; i< resultList.size(); i++){
            Object[] person = resultList.get(i);
            System.out.println("SB_REST_PERSON "+(i+1)+ " : ");
            for(int p=0; p<person.length; p++){
                System.out.print("[ "+ person[p] + " ] , ");
            }
            System.out.println("------------------");
        }
        return Response.ok("ok").build();
    }


    @GET
    @Path("personDTO")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonDTOFromDB(){
        List<PersonDTO> personList = new ArrayList<>();
        Query query = em.createNativeQuery("select * from SB_REST_PERSON");
        List<Object[]> resultList = query.getResultList();
        for(int i=0; i< resultList.size(); i++){
            Object[] person = resultList.get(i);
            personList.add(createPersonFromDbResult(person));
        }
        GenericEntity<List<PersonDTO>> personGE =
                new GenericEntity<List<PersonDTO>>(personList){};
        return Response.ok(personGE).build();
    }

    @GET
    @Path("withId/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonWithQuery(@PathParam("uuid") String uuidString){
        List<PersonDTO> personList = new ArrayList<>();
        Query query = em.createNativeQuery("select p.VERSION, p.UUID, p.DISPLAY_NAME, p.ADDRESS " +
                "from SB_REST_PERSON p  " +
                "where p.UUID = ?");

        query.setParameter(1, uuidString);
        List<Object[]> resultList = query.getResultList();

        for(int i=0; i< resultList.size(); i++){
            Object[] thePerson = resultList.get(i);
            PersonDTO personFromDbResult = createPersonFromDbResult(thePerson);
            personList.add(personFromDbResult);
        }
        GenericEntity<List<PersonDTO>> personGE =
                new GenericEntity<List<PersonDTO>>(personList){};
        return Response.ok(personGE).build();
    }

    /**
     *
     * @param uuidString
     * @param version
     * @return
     */
    @GET
    @Path("party/byVersionAndIdType")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPartiesByVersionAndIdType(@QueryParam("uuid") String uuidString,
                                                 @QueryParam("version") Integer version
                                                 ){
        List<PersonDTO> personList = new ArrayList<>();
        List<Object[]> resultList =
                em.createNativeQuery("select p.VERSION, p.UUID, p.DISPLAY_NAME, p.ADDRESS" +
                        " from SB_REST_PERSON p " +
                "where p.UUID = ?" +
                "and p.VERSION = ?")
                .setParameter(1, uuidString)
                .setParameter(2, version).getResultList();

        for(int i=0; i< resultList.size(); i++){
            Object[] partyDB = resultList.get(i);
            personList.add(createPersonFromDbResult(partyDB));
        }

        GenericEntity<List<PersonDTO>> partyGE =
                new GenericEntity<List<PersonDTO>>(personList){};
        return Response.ok(partyGE).build();
    }


    private PersonDTO createPersonFromDbResult(Object[] person) {
        PersonDTO aPerson = new PersonDTO();
        aPerson.setVersion((BigDecimal) person[0] );
        aPerson.setUuid((String)person[1]);
        aPerson.setDisplayName((String)person[2]);
        aPerson.setAddress((String)person[3]);
        return aPerson;
    }

}
