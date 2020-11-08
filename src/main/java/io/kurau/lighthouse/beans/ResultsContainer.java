package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static java.util.Arrays.asList;

@Getter@Setter
public class ResultsContainer {

    private MetricResult firstContentfulPaint;
    private MetricResult speedIndex;
    private MetricResult largestContentfulPaint;
    private MetricResult interactive;
    private MetricResult totalBlockingTime;
    private MetricResult cumulativeLayoutShift;
    private MetricResult serverResponseTime;

    public List<MetricResult> toList() {
        return asList(firstContentfulPaint, speedIndex, largestContentfulPaint,
                interactive, totalBlockingTime, cumulativeLayoutShift,serverResponseTime);
    }

    public ResultsDiff diff(ResultsContainer toCompare) {
        ResultsDiff diff = new ResultsDiff();
        diff.setFirstContentfulPaint(compareMed(firstContentfulPaint, toCompare.getFirstContentfulPaint()));
        diff.setSpeedIndex(compareMed(speedIndex, toCompare.getSpeedIndex()));
        diff.setLargestContentfulPaint(compareMed(largestContentfulPaint, toCompare.getLargestContentfulPaint()));
        diff.setInteractive(compareMed(interactive, toCompare.getInteractive()));
        diff.setTotalBlockingTime(compareMed(totalBlockingTime, toCompare.getTotalBlockingTime()));
        diff.setCumulativeLayoutShift(compareMed(cumulativeLayoutShift, toCompare.getCumulativeLayoutShift()));
        diff.setServerResponseTime(compareMed(serverResponseTime, toCompare.getServerResponseTime()));
        return diff;
    }

    private MetricDiff compareMed(MetricResult actual, MetricResult toCompare) {
        long diffMed = actual.getMed() - toCompare.getMed();
        MetricDiff metricDiff = new MetricDiff()
                .setTitle(actual.getTitle())
                .setActualMed(actual.getMed())
                .setToCompareMed(toCompare.getMed())
                .setDiffMed(Math.abs(diffMed));
        if (diffMed>0) {
            return metricDiff.print();
        } else {
            return metricDiff.setColor("background-color: #9cffa3;").print();
        }
    }
}
