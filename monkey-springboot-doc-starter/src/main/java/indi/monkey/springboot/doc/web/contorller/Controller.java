package indi.monkey.springboot.doc.web.contorller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import indi.monkey.commons.web.vo.Response;
import indi.monkey.springboot.doc.web.adapter.DocAdapter;
import indi.monkey.springboot.doc.web.dto.DocDownLoad;
import indi.monkey.springboot.doc.web.dto.DocUpload;
import indi.monkey.springboot.doc.web.service.BaseService;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Controller
@RequestMapping(value="doc",method = {RequestMethod.POST,RequestMethod.GET})
@Slf4j
public class Controller {
	
	@Resource
	BaseService service;
	
	@Resource
	DocAdapter docAdapter;
	
	@PostMapping("upload")
	@ResponseBody
	public Response<?> upload(@RequestParam("file") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
		if(!file.isEmpty()) {
			try {
				DocUpload docUpload = docAdapter.build(req, resp, file);
				String result = service.upload(docUpload);
				return Response.success(result);
			} catch (IOException e) {
				log.error("service execute exception:",e);
				return Response.error(e);
			}
		}
		log.error("file is empty!");
		return Response.error(new IllegalArgumentException("not exist file!"));
	}
	
	
	public void download(String id,HttpServletResponse resp) {
		byte[] data = service.download(id);
		DocDownLoad docDownLoad = docAdapter.build(id, data);
		
		//TODO
	}
}
