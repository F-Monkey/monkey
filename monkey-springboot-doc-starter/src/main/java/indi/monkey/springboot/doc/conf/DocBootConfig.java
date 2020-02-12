package indi.monkey.springboot.doc.conf;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import indi.monkey.springboot.doc.conf.prop.DocProperties;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableConfigurationProperties(DocProperties.class)
@ComponentScans({ @ComponentScan(basePackages = { "indi.monkey.springboot.doc" }) })
@Slf4j
public class DocBootConfig implements ApplicationRunner {
	@Resource
	DocProperties docProperties;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		initFileDir();
	}

	private final void initFileDir() {
		File f = new File(docProperties.getFileDir());
		if (!f.exists()) {
			log.info("file dir is not exists! start init file dir...");
			f.mkdir();
			log.info("init success");
		}
	}

	public String getDirPath() {
		return docProperties.getFileDir();
	}
}
