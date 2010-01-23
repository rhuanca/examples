package net.sumasoftware.ws;

import java.io.StringReader;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;

/**
 * @author Renan Huanca
 * @since Dec 22, 2009 5:00:13 PM
 */
public class WebServiceClient {

private static final String MESSAGE =
        "<message xmlns=\"http://tempuri.org\">Hello Web Service World</message>";

    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    public void setDefaultUri(String defaultUri) {
        webServiceTemplate.setDefaultUri(defaultUri);
    }

    // send to the configured default URI
    public void simpleSendAndReceive() {
        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);
        webServiceTemplate.sendSourceAndReceiveToResult(source, result);
    }

    // send to an explicit URI
    public void customSendAndReceive() {
        StreamSource source = new StreamSource(new StringReader(MESSAGE));
        StreamResult result = new StreamResult(System.out);

        webServiceTemplate.sendSourceAndReceiveToResult("http://localhost:8080/bis/dataService/",
            source, result);
    }

    public static void main(String args[]){
        WebServiceClient client = new WebServiceClient();
        System.out.println(">>> calling web service :)");
        client.setDefaultUri("http://localhost:8080/bis/dataService/");
        client.simpleSendAndReceive();
    } 
}
