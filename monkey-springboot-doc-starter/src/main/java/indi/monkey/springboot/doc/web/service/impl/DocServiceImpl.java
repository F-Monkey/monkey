package indi.monkey.springboot.doc.web.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import indi.monkey.springboot.doc.conf.DocBootConfig;
import indi.monkey.springboot.doc.tool.DocFile;
import indi.monkey.springboot.doc.tool.DocFileTranslation;
import indi.monkey.springboot.doc.web.dto.DocUpload;
import indi.monkey.springboot.doc.web.service.BaseService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocServiceImpl implements BaseService {

	private final ConcurrentHashMap<String, String> FILE_PATH_CACHE = new ConcurrentHashMap<String, String>();

	@Resource
	DocBootConfig docBootConfig;

	@Override
	public String upload(DocUpload docUpload) {
		String id = UUID.randomUUID().toString();
		String dir = docBootConfig.getDirPath();
		String fileName = DocFile.genFilePath(dir, id, docUpload.getToFileSuffix());
		DocFileTranslation.transfor(docUpload.getToFileSuffix(), fileName, docUpload.getData());
		// DB层保存 // 先本地缓存
		FILE_PATH_CACHE.put(id, fileName);
		return id;
	}

	@Override
	public byte[] download(String id) {
		String filePath = FILE_PATH_CACHE.get(id);
		File f = new File(filePath);
		int tryCount = 0;
		int maxCount = 10;
		int sleepTime = 500;
		while (tryCount < maxCount) {
			try {
				return DocFile.toStreamBytes(f);
			} catch (IOException e) {
				log.error("get file bytes array error:{}", e);
			}
			tryCount++;
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
			log.info("try to get result file for {} times", tryCount);
		}
		log.error("after {}s still can not get result, abonden!", maxCount * sleepTime / 1000);
		throw new RuntimeException(
				String.format("after %d s still can not get result, abonden!", maxCount * sleepTime / 1000));
	}
	
}
