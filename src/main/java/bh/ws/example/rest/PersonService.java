package bh.ws.example.rest;

import bh.ws.example.domain.PartyDTO;
import bh.ws.example.domain.Person;

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
import java.sql.Timestamp;
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
        return Response.ok("ok").build();
    }


    @GET
    @Path("personDTO")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonDTOFromDB(){
        List<Person> personList = new ArrayList<>();
        Query query = em.createNativeQuery("select * from BR_PERSON");
        List<Object[]> resultList = query.getResultList();
        for(int i=0; i< resultList.size(); i++){
            Object[] person = resultList.get(i);
            Person aPerson = createPersonFromDbResult(person);

            personList.add(aPerson);
        }
        GenericEntity<List<Person>> personGE =
                new GenericEntity<List<Person>>(personList){};
        return Response.ok(personGE).build();
    }

    @GET
    @Path("withId/{givenId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonWithQuery(@PathParam("givenId") Integer givenId){
        List<Person> personList = new ArrayList<>();
        Query query = em.createNativeQuery(
                "select * from BR_PERSON p where p.PARTY_ID = ?");

        query.setParameter(1, givenId);
        List<Object[]> resultList = query.getResultList();

        for(int i=0; i< resultList.size(); i++){
            Object[] thePerson = resultList.get(i);
            Person personFromDbResult = createPersonFromDbResult(thePerson);
            personList.add(personFromDbResult);
        }
        GenericEntity<List<Person>> personGE =
                new GenericEntity<List<Person>>(personList){};
        return Response.ok(personGE).build();
    }


    @GET
    @Path("party/byVersionAndIdType")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPartiesByVersionAndIdType(@QueryParam("idType") String idType,
                                                 @QueryParam("version") Integer version
                                                 ){
        List<PartyDTO> partyList = new ArrayList<>();
        List<Object[]> resultList =
                em.createNativeQuery("select p.VERSION, p.DISPLAY_NAME, p.PRIMARY_IDENTIFICATION_TYPE" +
                        " from BR_PARTY p " +
                "where p.PRIMARY_IDENTIFICATION_TYPE = ?" +
                "and p.VERSION = ?")
                .setParameter(1, idType)
                .setParameter(2, version).getResultList();

        for(int i=0; i< resultList.size(); i++){
            Object[] partyDB = resultList.get(i);

            PartyDTO party = new PartyDTO();
            party.setVersion((BigDecimal)partyDB[0]);
            party.setDisplayName((String)partyDB[1]);
            party.setPrimaryIdentificationType((String)partyDB[2]);

            partyList.add(party);
        }

        GenericEntity<List<PartyDTO>> partyGE =
                new GenericEntity<List<PartyDTO>>(partyList){};
        return Response.ok(partyGE).build();
    }







    private Person createPersonFromDbResult(Object[] person) {
        Person aPerson = new Person();
        aPerson.setCitizenshipCode((String)person[0]);
        aPerson.setBirthdate((Timestamp)person[1]);
        //WE DO NOT WANT DEATH DATE  = person[2]
        aPerson.setFatherName((String)person[3]);
        aPerson.setName((String)person[4]);
        aPerson.setGender((String)person[5]);
        aPerson.setLastName((String)person[6]);
        aPerson.setPartyId((BigDecimal)person[9]);
        return aPerson;
    }

}
