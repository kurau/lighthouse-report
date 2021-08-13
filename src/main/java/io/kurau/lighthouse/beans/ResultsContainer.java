package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    private List<MetricResult> extMetric = new ArrayList<>();

    public List<MetricResult> toList() {
        return asList(largestContentfulPaint, totalBlockingTime, cumulativeLayoutShift, firstContentfulPaint,
                speedIndex, interactive, serverResponseTime);
    }

    public List<MetricResultExt> toExtList() {
        List<MetricResultExt> resultExt = new ArrayList<>();
        extMetric.forEach(em -> resultExt.add(em.toExt()));
        return resultExt;
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

        // diff
        extMetric.forEach(em -> {
            String metricTitle = em.getTitle();
            Optional<MetricResult> metricResult =  toCompare.getExtMetric().stream()
                    .filter(m -> m.getTitle().equals(metricTitle)).findFirst();
            metricResult.ifPresent(result -> diff.getExtMetric().add(compareMed(em, result)));
        });

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
