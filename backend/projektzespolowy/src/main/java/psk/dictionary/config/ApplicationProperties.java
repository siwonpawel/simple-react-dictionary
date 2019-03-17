package psk.dictionary.config;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//@Component
//@ConfigurationProperties("application")
//@Validated
public class ApplicationProperties {

	@NotNull
	@Size(min=1)
	private String uploaddir;
	
	public String getUploaddir() {
		return uploaddir;
	}
	
	public void setUploaddir(String uploaddir) {
		this.uploaddir = uploaddir;
	}
}
