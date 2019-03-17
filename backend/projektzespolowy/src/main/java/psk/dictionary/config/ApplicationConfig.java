package psk.dictionary.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

//@Configuration
//@EnableConfigurationProperties({ApplicationProperties.class})
public class ApplicationConfig {
	
	private final ApplicationProperties applicationProperties;
	
	public ApplicationConfig(ApplicationProperties properties) {
		this.applicationProperties = properties;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		try {
			resolver.setUploadTempDir(new FileSystemResource(applicationProperties.getUploaddir()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resolver;
	}
}
