
package com.hero.wireless.http.connector.mms.yuefaninterface;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MmsOperatorImpService", targetNamespace = "http://mms.jwsserver.server.ema.ctc.com/", wsdlLocation = "http://101.132.222.2:8899/sms/webService/mmsOper?wsdl")
public class MmsOperatorImpService
    extends Service
{

    private final static URL MMSOPERATORIMPSERVICE_WSDL_LOCATION;
    private final static WebServiceException MMSOPERATORIMPSERVICE_EXCEPTION;
    private final static QName MMSOPERATORIMPSERVICE_QNAME = new QName("http://mms.jwsserver.server.ema.ctc.com/", "MmsOperatorImpService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://101.132.222.2:8899/sms/webService/mmsOper?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MMSOPERATORIMPSERVICE_WSDL_LOCATION = url;
        MMSOPERATORIMPSERVICE_EXCEPTION = e;
    }

    public MmsOperatorImpService() {
        super(__getWsdlLocation(), MMSOPERATORIMPSERVICE_QNAME);
    }

    public MmsOperatorImpService(WebServiceFeature... features) {
        super(__getWsdlLocation(), MMSOPERATORIMPSERVICE_QNAME, features);
    }

    public MmsOperatorImpService(URL wsdlLocation) {
        super(wsdlLocation, MMSOPERATORIMPSERVICE_QNAME);
    }

    public MmsOperatorImpService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MMSOPERATORIMPSERVICE_QNAME, features);
    }

    public MmsOperatorImpService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MmsOperatorImpService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns IMmsOperator
     */
    @WebEndpoint(name = "MmsOperatorImpPort")
    public IMmsOperator getMmsOperatorImpPort() {
        return super.getPort(new QName("http://mms.jwsserver.server.ema.ctc.com/", "MmsOperatorImpPort"), IMmsOperator.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IMmsOperator
     */
    @WebEndpoint(name = "MmsOperatorImpPort")
    public IMmsOperator getMmsOperatorImpPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://mms.jwsserver.server.ema.ctc.com/", "MmsOperatorImpPort"), IMmsOperator.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MMSOPERATORIMPSERVICE_EXCEPTION!= null) {
            throw MMSOPERATORIMPSERVICE_EXCEPTION;
        }
        return MMSOPERATORIMPSERVICE_WSDL_LOCATION;
    }

}
