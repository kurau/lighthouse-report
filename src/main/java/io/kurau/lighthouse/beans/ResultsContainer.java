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
        return asList(largestContentfulPaint, totalBlockingTime, cumulativeLayoutShift, firstContentfulPaint,
                speedIndex, interactive, serverResponseTime);
    }

    public ResultsDiff diff(ResultsContainer toCompare) {
        ResultsDiff diff = new ResultsDiff();

        diff.setLargestContentfulPaint(compareMed(largestContentfulPaint,
                toCompare.getLargestContentfulPaint()).setImportant(true));
        diff.setCumulativeLayoutShift(compareMed(cumulativeLayoutShift,
                toCompare.getCumulativeLayoutShift()).setImportant(true));
        diff.setTotalBlockingTime(compareMed(totalBlockingTime,
                toCompare.getTotalBlockingTime()).setImportant(true));

        diff.setFirstContentfulPaint(compareMed(firstContentfulPaint, toCompare.getFirstContentfulPaint()));
        diff.setSpeedIndex(compareMed(speedIndex, toCompare.getSpeedIndex()));
        diff.setInteractive(compareMed(interactive, toCompare.getInteractive()));
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
