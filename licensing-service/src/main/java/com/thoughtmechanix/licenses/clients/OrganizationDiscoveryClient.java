package com.thoughtmechanix.licenses.clients;

import com.thoughtmechanix.licenses.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Problems with this approach:

   1. You are not taking advantage of Ribbon’s client side load-balancing—By calling the DiscoveryClient directly, you get back a list of services,
      but it becomes your responsibility to choose which service instances returned you’re going to invoke.
   2. you’re directly instantiating the RestTemplate class in the code. This is antithetical to normal Spring REST invocations,
      as normally you’d have the Spring Framework inject the RestTemplate the class using it via the @Autowired annotation.
   3. because once you’ve enabled the Spring DiscoveryClient in the application class via the @EnableDiscoveryClient annotation, all RestTemplates
      managed by the Spring framework will have a Ribbon-enabled interceptor injected into them that will change how URLs are created with the
      RestTemplate class. Directly instantiating the RestTemplate class allows you to avoid this behavior.
 */
@Component
public class OrganizationDiscoveryClient {

    @Autowired
    // Class used to interact with Ribbon
    private DiscoveryClient discoveryClient;

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryClient.class);

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();

        //The ServiceInstance class is used to hold information about a specific instance of a service including its hostname, port and URI.
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice"); //passing in the key of service you’re looking for

        System.out.println("(### OrganizationDiscoveryClient.getOrganization) available instances - size: " + instances.size());
        ServiceInstance instance = instances.get(0);
        logger.info(new StringBuilder("ServiceId: ").append(instance.getServiceId()).append("\n")
                                         .append("Host:").append(instance.getHost()).append("\n")
                                         .append("Port: ").append(instance.getPort()).append("\n")
                                         .append("Uri: ").append(instance.getUri()).toString());
        logger.info("");

        if (instances.size()==0) return null;
        String serviceUri = String.format("%s/v1/organizations/%s",instances.get(0).getUri().toString(), organizationId);

        // Once you have a target URL, you can use a standard Spring RestTemplate to call your organization service and retrieve data.
        ResponseEntity< Organization > restExchange =
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
