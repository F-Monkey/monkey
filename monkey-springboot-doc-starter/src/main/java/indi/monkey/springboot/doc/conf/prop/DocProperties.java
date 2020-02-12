package indi.monkey.springboot.doc.conf.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "indi.monkey.doc")
public class DocProperties {
	private String sign;
	
	private String fileDir;
}
