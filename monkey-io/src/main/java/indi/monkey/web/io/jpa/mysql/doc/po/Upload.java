package indi.monkey.web.io.jpa.mysql.doc.po;

import javax.persistence.Table;

import indi.monkey.web.io.po.JPABaseBean;
import lombok.Data;

@Data
@Table(name = "upload")
public class Upload extends JPABaseBean {
	private String filePath;
	private String fromSuffix;
	private String toSuffix;
	private String uid;
}
