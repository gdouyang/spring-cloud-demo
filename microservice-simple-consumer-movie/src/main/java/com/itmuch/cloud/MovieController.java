package com.itmuch.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MovieController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@GetMapping("/user/{id}")
	public User findById(@PathVariable Long id) {
		return this.restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
	}

	@GetMapping("/user-instance")
	public List<ServiceInstance> showInfo() {
		List<ServiceInstance> instancesById = discoveryClient.getInstances("microservice-provider-user");
		return instancesById;
	}

	@GetMapping("/log-instance")
	public String logUserInstance() {
		ServiceInstance instancesById = loadBalancerClient.choose("microservice-provider-user");
		return String.format("%s:%s:%s", instancesById.getServiceId(), instancesById.getHost(),
				instancesById.getPort());
	}
}
