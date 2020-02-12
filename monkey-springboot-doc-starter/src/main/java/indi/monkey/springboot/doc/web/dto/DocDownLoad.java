package indi.monkey.springboot.doc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocDownLoad {
	private String suffix;
	private byte[] data;
	private int size;
}
