package pearls.mapping;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.Scanner;

public class TestMain {
	
	public static String sourcePath = "/Users/snownettle/Documents/HPI/4.Semester/Pearls/implementation/templates/adept/RequestForTimeOff.xml";
	public static String targetPath = "/Users/snownettle/Documents/HPI/4.Semester/Pearls/implementation/templates/demo.xml";
	
    public static void main(String[] args) throws IOException, URISyntaxException, TransformerException, ParserConfigurationException, SAXException, XPathExpressionException {

        
        System.out.println("Start parsing ADEPT2 file");
        BPMNElements bpmn =  Adept2Transormation.test(sourcePath);
        System.out.println("Start creating BPMN file");
        CreateBMPNFile bpmnFile = new CreateBMPNFile();
        bpmnFile.setFile(targetPath);
        try {
			bpmnFile.saveConfig(bpmn);
			@SuppressWarnings("resource")
			String content = new Scanner(new File(targetPath)).useDelimiter("\\Z").next();
			String formated = XMLFormatter.format(content);
			PrintWriter out = new PrintWriter(targetPath);
			out.println(formated);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}