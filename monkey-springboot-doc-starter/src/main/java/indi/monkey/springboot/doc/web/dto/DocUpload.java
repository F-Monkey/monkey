package indi.monkey.springboot.doc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocUpload {
	private String requestId;
	private String fileName;
	private String fromFileSuffix;
	private String toFileSuffix;
	private byte[] data;
}
