package net.sumasoftware.ws;

import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.xpath.XPath;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Renan Huanca
 * @since Dec 21, 2009 12:03:56 PM
 */
public class HolidayEndpoint extends AbstractJDomPayloadEndpoint{
    private XPath startDateExpression;

        private XPath endDateExpression;

        private XPath nameExpression;

        private final HumanResourceService humanResourceService;

        public HolidayEndpoint(HumanResourceService humanResourceService) throws JDOMException {
            this.humanResourceService = humanResourceService;
            Namespace namespace = Namespace.getNamespace("hr", "http://mycompany.com/hr/schemas");
            startDateExpression = XPath.newInstance("//hr:StartDate");
            startDateExpression.addNamespace(namespace);
            endDateExpression = XPath.newInstance("//hr:EndDate");
            endDateExpression.addNamespace(namespace);
            nameExpression = XPath.newInstance("concat(//hr:FirstName,' ',//hr:LastName)");
            nameExpression.addNamespace(namespace);
        }

        protected Element invokeInternal(Element holidayRequest) throws Exception {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateExpression.valueOf(holidayRequest));
            Date endDate = dateFormat.parse(endDateExpression.valueOf(holidayRequest));
            String name = nameExpression.valueOf(holidayRequest);

            humanResourceService.bookHoliday(startDate, endDate, name);
            return null;
        }
    
    
}
