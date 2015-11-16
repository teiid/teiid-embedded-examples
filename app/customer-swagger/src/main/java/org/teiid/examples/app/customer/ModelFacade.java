package org.teiid.examples.app.customer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "facade")
public class ModelFacade {
    
    private Customer customer;
    
    private CustomerStatus customerStatus;
    
    private Types types;
    
    private Success success;
    
    private List<Success> successlist;
    
    private Set<Success> successset;
    
    @XmlElement
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @XmlElement
    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
    }

    @XmlElement
    public Types getTypes() {
        return types;
    }

    public void setTypes(Types types) {
        this.types = types;
    }

    @XmlElement
    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    @XmlElement
    public List<Success> getSuccesslist() {
        return successlist;
    }

    public void setSuccesslist(List<Success> successlist) {
        this.successlist = successlist;
    }

    @XmlElement
    public Set<Success> getSuccessset() {
        return successset;
    }

    public void setSuccessset(Set<Success> successset) {
        this.successset = successset;
    }

}
