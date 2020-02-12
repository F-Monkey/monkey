package indi.monkey.springboot.doc.tool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocFileTranslation {
	private DocFileTranslation() {
	}

	public static byte[] transfor(String suffix, byte[] bytes, String fileName) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PdfDocument pdfDocument = new PdfDocument();
		pdfDocument.loadFromBytes(bytes);
		FileFormat format = FileFormat.valueOf(suffix.toUpperCase());
		if (format == null) {
			log.error("unsupport fileFormat:{}", suffix);
			throw new IllegalArgumentException("unsupport fileFormat:" + suffix);
		}
		if (fileName != null) {
			pdfDocument.saveToFile(fileName,format);
			return null;
		}
		pdfDocument.saveToStream(outputStream, format);
		return outputStream.toByteArray();
	}
	
	public static byte[] transfor(String suffix, File file, String fileName) throws IOException {
		byte[] bytes = DocFile.toStreamBytes(file);
		return transfor(suffix, bytes, fileName);
	}
	
	public void saveFile(String fileName, byte[] bytes) {

	}

	@AllArgsConstructor
	private static final class MyThread implements Runnable {

		private String fileName;
		private String suffix;
		private byte[] bytes;

		@Override
		public void run() {
			transfor(suffix, bytes, fileName);
		}
	}

	private static ExecutorService EXECUTOR_SERVICE;
	static {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		EXECUTOR_SERVICE = new ThreadPoolExecutor(1, availableProcessors * 2, 1, TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<Runnable>(1000));
	}

	public static void transfor(String suffix, String fileName, byte[] bytes) {
		MyThread myThread = new MyThread(fileName, suffix, bytes);
		EXECUTOR_SERVICE.execute(myThread);
	}
}
