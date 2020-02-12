package indi.monkey.springboot.doc.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DocFile {
	public static String genFilePath(String dir, String id, String suffix) {
		return dir + File.separator + id + "." + suffix;
	}

	public static byte[] toStreamBytes(File f) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		FileInputStream fis = new FileInputStream(f);
		int len = -1;
		while ((len = fis.read(bytes)) != -1) {
			baos.write(bytes, 0, len);
			baos.flush();
		}
		fis.close();
		return baos.toByteArray();
	}
}
