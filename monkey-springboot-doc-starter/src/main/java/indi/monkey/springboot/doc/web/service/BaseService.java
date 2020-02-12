package indi.monkey.springboot.doc.web.service;

import indi.monkey.springboot.doc.web.dto.DocUpload;

public interface BaseService {
	String upload(DocUpload docUpload);

	byte[] download(String id);
}
