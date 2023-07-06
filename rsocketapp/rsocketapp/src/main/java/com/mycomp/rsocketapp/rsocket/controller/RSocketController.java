package com.mycomp.rsocketapp.rsocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycomp.rsocketapp.collections.OrganizationList;
import com.mycomp.rsocketapp.collections.PersonList;
import com.mycomp.rsocketapp.model.FilterData;
import com.mycomp.rsocketapp.model.Organization;
import com.mycomp.rsocketapp.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Controller
@AllArgsConstructor
public class RSocketController {

    private PersonList personList;
    private OrganizationList organizationList;
    @MessageMapping("helloWorld")
    public Mono<String> getHelloWorld(){
        return Mono.just("hello world");
    }
    @MessageMapping("listOfPersons")
    public Flux<Person> getPersons(){
        return Flux.fromStream(personList.getPersonList().stream()).delaySequence(Duration.ofMillis(100));
    }
    @MessageMapping("listOfOrganizations")
    public Flux<Organization> getOrganizations(){
        return Flux.fromStream(organizationList.getOrganizations().stream());
    }
    @MessageMapping("filterOrganizations")
    public Flux<Organization> filterOrganizations(FilterData filterData) throws JsonProcessingException {
        System.out.println("Incoming Data: \n"+new ObjectMapper().writeValueAsString(filterData));
        return Flux.fromStream(organizationList.getOrganizations().stream())
                .delayElements(Duration.ofMillis(10))
                .filter(organization -> (organization.getName().contains(filterData.getOrganization().getName()) &&
                        organization.getCountry().contains(filterData.getOrganization().getCountry()) &&
                        organization.getNumberOfEmployees() > filterData.getOrganization().getNumberOfEmployees()));
    }
}

