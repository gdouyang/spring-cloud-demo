package spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置服务器<br>
 * http://localhost:8080/microservice-foo/dev
 * http://localhost:8080/microservice-foo-dev.properties
 * http://localhost:8080/microservice-foo-dev.yml
 *
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
