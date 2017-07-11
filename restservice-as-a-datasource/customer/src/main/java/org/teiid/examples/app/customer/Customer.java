/*
 * Copyright Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags and
 * the COPYRIGHT.txt file distributed with this work.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.teiid.examples.app.customer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 4766793820542316647L;
    
    private String customernumber;
    
    private String customername;
    
    private String contactlastname;
    
    private String contactfirstname;
    
    private String phone;
    
    private String addressline1;
    
    private String addressline2;
    
    private String city;
    
    private String state;
    
    private String postalcode;
    
    private String country;
    
    private String salesrepemployeenumber;
    
    private String creditlimit;

    @XmlElement(name = "customernumber")
    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }

    @XmlElement(name = "customername")
    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    @XmlElement(name = "contactlastname")
    public String getContactlastname() {
        return contactlastname;
    }

    public void setContactlastname(String contactlastname) {
        this.contactlastname = contactlastname;
    }

    @XmlElement(name = "contactfirstname")
    public String getContactfirstname() {
        return contactfirstname;
    }

    public void setContactfirstname(String contactfirstname) {
        this.contactfirstname = contactfirstname;
    }

    @XmlElement(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement(name = "addressline1")
    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    @XmlElement(name = "addressline2")
    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    @XmlElement(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlElement(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlElement(name = "postalcode")
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @XmlElement(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @XmlElement(name = "salesrepemployeenumber")
    public String getSalesrepemployeenumber() {
        return salesrepemployeenumber;
    }

    public void setSalesrepemployeenumber(String salesrepemployeenumber) {
        this.salesrepemployeenumber = salesrepemployeenumber;
    }

    @XmlElement(name = "creditlimit")
    public String getCreditlimit() {
        return creditlimit;
    }

    public void setCreditlimit(String creditlimit) {
        this.creditlimit = creditlimit;
    }

    @Override
    public String toString() {
        return "Customer [customernumber=" + customernumber + ", customername="
                + customername + ", contactlastname=" + contactlastname
                + ", contactfirstname=" + contactfirstname + ", phone=" + phone
                + ", addressline1=" + addressline1 + ", addressline2="
                + addressline2 + ", city=" + city + ", state=" + state
                + ", postalcode=" + postalcode + ", country=" + country
                + ", salesrepemployeenumber=" + salesrepemployeenumber
                + ", creditlimit=" + creditlimit + "]";
    }
    

}
