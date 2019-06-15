package com.aliware.tianchi.stats;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Born
 */
public class DataCollector {

    private AtomicInteger activeRequests = new AtomicInteger(0);
    private BucketRate bucketRate = new BucketRate(300);
    private MeasuredRate qps = new MeasuredRate(300);
    private DistributionRate distributionRate = new DistributionRate(300);


    public void incrementRequests() {
        activeRequests.incrementAndGet();
    }

    public void decrementRequests() {
        activeRequests.decrementAndGet();
    }

    public int getActive() {
        return activeRequests.get();
    }


    public void succeedRequest() {
        qps.increment();
    }

    public int getQPS() {
        return qps.getCount();
    }

    public int getMaxQPS() {
        return qps.getMaxCount();
    }


    public void setBucket(int i) {
        bucketRate.setBucket(i);
    }

    public int getBucket() {
        return bucketRate.getCount();
    }

    public int getMaxBucket() {
        return bucketRate.getMaxCount();
    }

    public void noteValue(long i) {
        distributionRate.noteValue(i);
    }

    public double getMean() {
        return distributionRate.getMean();
    }

}
