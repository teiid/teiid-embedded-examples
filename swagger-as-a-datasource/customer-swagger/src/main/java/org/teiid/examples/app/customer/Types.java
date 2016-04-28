package org.teiid.examples.app.customer;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "types")
public class Types implements Serializable {

    private static final long serialVersionUID = 2276816700787022272L;

    public static Types sampleTypes() {
        Types t = new Types();
        t.setA((byte)127);
        t.setB((short)32767);
        t.setC(2147483647);
        t.setD(9223372036854775807L);
        t.setE(234.5f);
        t.setF(123.4);
        t.setG(true);
        t.setH('k');
        t.setI("Kylin".getBytes());
        t.setJ("Kylin");
        t.setK(new Integer(100));
        t.setL(new Date());
        t.setM(new Time(new Date().getTime()));
        t.setN(new Timestamp(new Date().getTime()));
        return t;
    }
    
    private byte a;
    
    private short b;
    
    private int c;
    
    private long d;
    
    private float e;
    
    private double f;
    
    private boolean g;
    
    private char h;
    
    private byte[] i;
    
    private String j;
    
    private Integer k;
    
    private Date l;
    
    private Time m;
    
    private Timestamp n;

    @XmlElement(name = "a")
    public byte getA() {
        return a;
    }

    public void setA(byte a) {
        this.a = a;
    }

    @XmlElement(name = "b")
    public short getB() {
        return b;
    }

    public void setB(short b) {
        this.b = b;
    }

    @XmlElement(name = "c")
    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @XmlElement(name = "d")
    public long getD() {
        return d;
    }

    public void setD(long d) {
        this.d = d;
    }

    @XmlElement(name = "e")
    public float getE() {
        return e;
    }

    public void setE(float e) {
        this.e = e;
    }

    @XmlElement(name = "f")
    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    @XmlElement(name = "g")
    public boolean isG() {
        return g;
    }

    public void setG(boolean g) {
        this.g = g;
    }

    @XmlElement(name = "h")
    public char getH() {
        return h;
    }

    public void setH(char h) {
        this.h = h;
    }

    @XmlElement(name = "i")
    public byte[] getI() {
        return i;
    }

    public void setI(byte[] i) {
        this.i = i;
    }

    @XmlElement(name = "j")
    public String getJ() {
        return j;
    }

    public void setJ(String j) {
        this.j = j;
    }

    @XmlElement(name = "k")
    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    @XmlElement(name = "l")
    public Date getL() {
        return l;
    }

    public void setL(Date l) {
        this.l = l;
    }

    @XmlElement(name = "m")
    public Time getM() {
        return m;
    }

    public void setM(Time m) {
        this.m = m;
    }

    @XmlElement(name = "n")
    public Timestamp getN() {
        return n;
    }

    public void setN(Timestamp n) {
        this.n = n;
    }
    
    

}
