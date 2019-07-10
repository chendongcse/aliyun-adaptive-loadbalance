package com.aliware.tianchi.stats;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Born
 */
public class DataCollector {


    public static final double ALPHA = 1;
    public static final double BETA = 0.20;
    public static final double GAMMA = 1.18;

    public static final int COLLECT = 300;


    private volatile int bucket = 1000;
    private AtomicInteger activeRequests = new AtomicInteger(0);
    private ThroughputRate throughputRate = new ThroughputRate(COLLECT);
    private ThroughputRate totalRate;

    private double rate = 1.0;

    public DataCollector(ThroughputRate totalRate) {
        this.totalRate = totalRate;
    }


    public void incrementRequests() {
        activeRequests.incrementAndGet();
    }

    public void failedRequest() {
    }

    public void decrementRequests() {
        activeRequests.decrementAndGet();
        throughputRate.note();
        totalRate.note();
    }

    public void setBucket(int bucket) {
        this.bucket = bucket;
    }

    public void succeedRequest() {
    }


    public int getActive() {
        return activeRequests.get();
    }

    public int getBucket() {
        return bucket;
    }


    public int getWeight() {
        double weight = this.throughputRate.getWeight();

        weight = weight * rate;

        return new Double(weight).intValue();
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ThroughputRate getThroughputRate() {
        return throughputRate;
    }

    public DataCollectorCopy copy() {
        return new DataCollectorCopy(getActive(), getBucket(), getWeight(),
                new Double(this.throughputRate.getThroughputRate()).intValue(),
                new Double(totalRate.getThroughputRate()).intValue());
    }

    public static class DataCollectorCopy{

        public DataCollectorCopy(int active, int bucket, int weight,int throughput,int total) {
            this.active = active;
            this.bucket = bucket;
            this.weight = weight;
            this.throughput = throughput;
            this.total = total;
        }

        private int active;
        private int bucket;
        private int weight;
        private int throughput;
        private int total;

        public int getActive() {
            return active;
        }


        public int getBucket() {
            return bucket;
        }


        public int getWeight() {
            return weight;
        }

        public int getThroughput() {
            return throughput;
        }

        public int getTotal() {
            return total;
        }
    }
}
