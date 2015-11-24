package org.teiid.examples.app.customer;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "time-types")
public class TimeTypes {
    
    public static TimeTypes create() {
        TimeTypes t = new TimeTypes();
        
        t.setDate(new Date());
        t.setSqlDate(new java.sql.Date(System.currentTimeMillis()) );
        t.setSqlTime(new java.sql.Time(System.currentTimeMillis()));
        t.setSqlTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        
        return t;
    }
    
    private Date date;
    
    private java.sql.Date sqlDate;
    
    private java.sql.Time sqlTime;
    
    private java.sql.Timestamp sqlTimestamp;

    @XmlElement
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @XmlElement
    public java.sql.Date getSqlDate() {
        return sqlDate;
    }

    public void setSqlDate(java.sql.Date sqlDate) {
        this.sqlDate = sqlDate;
    }

    @XmlElement
    public java.sql.Time getSqlTime() {
        return sqlTime;
    }

    public void setSqlTime(java.sql.Time sqlTime) {
        this.sqlTime = sqlTime;
    }

    @XmlElement
    public java.sql.Timestamp getSqlTimestamp() {
        return sqlTimestamp;
    }

    public void setSqlTimestamp(java.sql.Timestamp sqlTimestamp) {
        this.sqlTimestamp = sqlTimestamp;
    }

    @Override
    public String toString() {
        return "TimeTypes [date=" + date + ", sqlDate=" + sqlDate
                + ", sqlTime=" + sqlTime + ", sqlTimestamp=" + sqlTimestamp
                + "]";
    }
    
    public static void main(String[] args) {
        System.out.println(TimeTypes.create());
    }

}
