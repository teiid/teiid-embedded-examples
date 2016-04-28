package org.teiid.examples.app.customer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "success")
public class Success implements Serializable  {

    private static final long serialVersionUID = -6638420672570901777L;
    
    private String success;
    
    public Success() {
        
    }

    public Success(String success) {
        super();
        this.success = success;
    }

    @XmlElement(name = "success")
    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

}
