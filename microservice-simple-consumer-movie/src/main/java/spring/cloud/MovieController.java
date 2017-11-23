package spring.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class MovieController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private LoadBalancerClient loadBalancerClient;

	@Autowired
	private UserFeignClient userFeignClient;

	@HystrixCommand(fallbackMethod = "findByIdFallback")
	@GetMapping(value = "/user/{id}", produces = { "application/json;charset=UTF-8" })
	public User findById(@PathVariable Long id) {
		User forObject = this.restTemplate.getForObject("http://microservice-provider-user/" + id, User.class);
		return forObject;
	}

	public User findByIdFallback(Long id) {
		User user = new User();
		user.setId(-1L);
		user.setName("default user");
		return user;
	}

	@GetMapping("/user1/{id}")
	public User findById1(@PathVariable Long id) {
		return this.userFeignClient.findById(id);
	}

	@GetMapping("/user-instance")
	public List<ServiceInstance> showInfo() {
		List<ServiceInstance> instancesById = discoveryClient.getInstances("microservice-provider-user");
		return instancesById;
	}

	@HystrixCommand(fallbackMethod = "findByIdFallback1")
	@GetMapping("/log-instance")
	public String logUserInstance() {
		ServiceInstance instancesById = loadBalancerClient.choose("microservice-provider-user");
		return String.format("%s:%s:%s", instancesById.getServiceId(), instancesById.getHost(),
				instancesById.getPort());
	}

	public String findByIdFallback1() {
		return "";
	}
}
