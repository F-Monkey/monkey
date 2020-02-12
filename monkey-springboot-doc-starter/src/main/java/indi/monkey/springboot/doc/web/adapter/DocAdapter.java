package indi.monkey.springboot.doc.web.adapter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import indi.monkey.springboot.doc.web.dto.DocDownLoad;
import indi.monkey.springboot.doc.web.dto.DocUpload;

@Component
public class DocAdapter implements Adapter {

	public DocUpload build(HttpServletRequest req, HttpServletResponse resp, MultipartFile file) throws IOException {
		String requestId = req.getParameter("requestId");
		String fromSuffix = req.getParameter("fromSuffix");
		String toSuffix = req.getParameter("toSuffix");
		String name = file.getName();
		byte[] data = file.getBytes();
		DocUpload docUpload = new DocUpload(requestId, name, fromSuffix, toSuffix, data);
		return docUpload;
	}

	public DocDownLoad build(String id, byte[] data) {
		return new DocDownLoad(null, data, data.length);
	}
}
