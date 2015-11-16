package org.teiid.examples.app.customer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "status")
public class CustomerStatus {

    private int size;
    
    private HeapSize heap;

    @XmlElement
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
    @XmlElement
    public HeapSize getHeap() {
        return heap;
    }

    public void setHeap(HeapSize heap) {
        this.heap = heap;
    }

    @XmlRootElement(name = "heap")
    public static class HeapSize{
        
        private long maxMemory;
        
        private long allocatedMemory;
        
        private long freeMemory;

        @XmlElement(name = "maxMemory")
        public long getMaxMemory() {
            return maxMemory;
        }

        public void setMaxMemory(long maxMemory) {
            this.maxMemory = maxMemory;
        }

        @XmlElement(name = "allocatedMemory")
        public long getAllocatedMemory() {
            return allocatedMemory;
        }

        public void setAllocatedMemory(long allocatedMemory) {
            this.allocatedMemory = allocatedMemory;
        }

        @XmlElement(name = "freeMemory")
        public long getFreeMemory() {
            return freeMemory;
        }

        public void setFreeMemory(long freeMemory) {
            this.freeMemory = freeMemory;
        }
    }

}
