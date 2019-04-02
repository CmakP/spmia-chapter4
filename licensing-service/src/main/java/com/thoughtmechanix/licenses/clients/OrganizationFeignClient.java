package com.thoughtmechanix.licenses.clients;


import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 Defining a Java interface and then annotating that interface with Spring Cloud annotations
 to map what Eureka-based service Ribbon will invoke. The Spring Cloud framework will
 dynamically generate a proxy class that will be used to invoke the targeted REST service.

 For FeignExceptionhandling @see: https://github.com/Netflix/feign/wiki/Custom-error-handling
 */
@FeignClient("organizationservice")
public interface OrganizationFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/v1/organizations/{organizationId}",
            consumes="application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
