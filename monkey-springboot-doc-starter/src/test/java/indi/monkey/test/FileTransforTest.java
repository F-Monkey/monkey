package indi.monkey.test;

import java.io.File;
import java.io.IOException;

import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;

import indi.monkey.springboot.doc.tool.DocFile;
import indi.monkey.springboot.doc.tool.DocFileTranslation;

public class FileTransforTest {
	public static void test01() throws IOException {
		File file = new File("src/test/java/indi/monkey/test/针灸穴名解（高式国） (1).pdf");
		PdfDocument document = new PdfDocument();
		document.loadFromFile("src/test/java/indi/monkey/test/针灸穴名解（高式国） (1).pdf");
		document.saveToFile("document.doc",FileFormat.DOC);
		String suffix = "DOCX";
		//byte[] bytes = DocFile.toStreamBytes(file);
		//DocFileTranslation.transfor(suffix, bytes, "111.DOCX");
	}
	
	public static void main(String[] args) throws IOException {
		test01();
	}
}
