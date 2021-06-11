
package com.hero.wireless.http.connector.mms.yuefaninterface;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.demo.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ComCtcEmaServerJwsserverMmsMmsFileGroup_QNAME = new QName("http://mms.jwsserver.server.ema.ctc.com/", "com.ctc.ema.server.jwsserver.mms.MmsFileGroup");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.demo.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MmsFileGroup }
     * 
     */
    public MmsFileGroup createMmsFileGroup() {
        return new MmsFileGroup();
    }

    /**
     * Create an instance of {@link MmsFileGroupArray }
     * 
     */
    public MmsFileGroupArray createMmsFileGroupArray() {
        return new MmsFileGroupArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MmsFileGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://mms.jwsserver.server.ema.ctc.com/", name = "com.ctc.ema.server.jwsserver.mms.MmsFileGroup")
    public JAXBElement<MmsFileGroup> createComCtcEmaServerJwsserverMmsMmsFileGroup(MmsFileGroup value) {
        return new JAXBElement<MmsFileGroup>(_ComCtcEmaServerJwsserverMmsMmsFileGroup_QNAME, MmsFileGroup.class, null, value);
    }

}
