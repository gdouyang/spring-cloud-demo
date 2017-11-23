package spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置服务器<br>
 * http://localhost:8080/microservice-foo/dev
 * http://localhost:8080/microservice-foo-dev.properties
 * http://localhost:8080/microservice-foo-dev.yml <br>
 * 
 * 端点与配置文件映射规则：
 * <ul>
 * <li>/{application}/{profile}[/{label}]</li>
 * <li>/{application}-{profile}.yml</li>
 * <li>/{label}/{application}-{profile}.yml</li>
 * <li>/{application}-{profile}.properties</li>
 * <li>/{label}/{application}-{profile}.properties</li>
 * </ul>
 * 
 * {application}微服务的名称(spring.appliction.name)； {label}对应Git仓库的分支，默认是mater；
 * 
 */
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}
}
