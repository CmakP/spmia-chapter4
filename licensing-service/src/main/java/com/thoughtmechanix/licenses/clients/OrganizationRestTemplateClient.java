package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {
    @Autowired
    RestTemplate restTemplate;

    public Organization getOrganization(String organizationId){
        ResponseEntity<Organization> restExchange =
                restTemplate.exchange(
                        /** When using a Ribbon-back RestTemplate, you build the target URL with the Eureka service ID,
                            rather than using the physical location of the service.
                            The server name in the URL matches the application ID of the organizationservice key
                            that you registered the organization service with in Eureka:
                        **/
                        "http://organizationservice/v1/organizations/{organizationId}",
                        HttpMethod.GET,
                        null, Organization.class, organizationId);
        System.out.println("");

        return restExchange.getBody();
    }
}
