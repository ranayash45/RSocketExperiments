package com.mycomp.rsocketclientapp.rsocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.rsocketclientapp.client.model.FilterData;
import com.mycomp.rsocketclientapp.client.model.Organization;
import com.mycomp.rsocketclientapp.client.model.Person;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.logging.Filter;

@RestController
public class RSocketClientController {

    private final RSocketRequester rSocketRequester;
    private final ObjectMapper objectMapper;

    public RSocketClientController(RSocketRequester rSocketRequester, ObjectMapper objectMapper) {
        this.rSocketRequester = rSocketRequester;
        this.objectMapper = objectMapper;
    }

    @RequestMapping(value = "/RSocket/Hello",produces = "application/json")
    public Mono<String> getRSocketHello(){
        return rSocketRequester.route("helloWorld").retrieveMono(String.class);
    }

    @RequestMapping(value = "/RSocket/Persons",produces = "application/json")
    public Flux<Person> getPersonList(){
        return rSocketRequester.route("listOfPersons").retrieveFlux(String.class).map(s -> {
            try {
                return objectMapper.readValue(s,Person.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
    @RequestMapping(value = "/RSocket/Person/{firstname}",produces = "application/json")
    public Flux<Person> getFilteredList(@PathVariable("firstname") String firstName){
        return getPersonList().filter(person -> person.getFirstName().startsWith(firstName));
    }
    @RequestMapping(value = "/RSocket/Organizations",produces = "application/json")
    public Flux<Organization> getOrganizations(){
        return rSocketRequester.route("listOfOrganizations")
                .retrieveFlux(String.class)
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, Organization.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .log();
    }
    @RequestMapping(value = "/RSocket/Organizations/{org_name}",produces = "application/json")
    public Flux<Organization> getOrganizations(@PathVariable("org_name")String org_name){
        return rSocketRequester.route("listOfOrganizations")
                .retrieveFlux(String.class)
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, Organization.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .filter(organization -> organization.getName().startsWith(org_name))
                .log();
    }
    @RequestMapping(value = "/RSocket/Organizations/filter",produces = "application/json")
    public Mono<List<Organization>> getOrginizationsBasedOnFilter(@RequestBody FilterData filterData) throws JsonProcessingException {
        return rSocketRequester
                .route("filterOrganizations")
                .data(objectMapper.writeValueAsString(filterData))
                .retrieveFlux(String.class)
                .map(s -> {
                    try {
                        return objectMapper.readValue(s, Organization.class);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collectList()
                .timeout(Duration.ofMillis(2000))
                .log();
    }

}
