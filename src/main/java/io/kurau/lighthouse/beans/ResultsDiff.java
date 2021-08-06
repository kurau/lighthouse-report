package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Getter@Setter
public class ResultsDiff {

    private MetricDiff firstContentfulPaint;
    private MetricDiff speedIndex;
    private MetricDiff largestContentfulPaint;
    private MetricDiff interactive;
    private MetricDiff totalBlockingTime;
    private MetricDiff cumulativeLayoutShift;
    private MetricDiff serverResponseTime;

    private List<MetricDiff> extMetric = new ArrayList<>();

    public List<MetricDiff> toList() {
        return asList(largestContentfulPaint, totalBlockingTime, cumulativeLayoutShift, firstContentfulPaint,
                speedIndex, interactive, serverResponseTime);
    }

    public List<MetricDiffExt> toExtList() {
        List<MetricDiffExt> resultExt = new ArrayList<>();
        extMetric.forEach(em -> resultExt.add(em.toExt()));
        return resultExt;
    }

}
