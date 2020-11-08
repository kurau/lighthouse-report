package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;

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

    public List<MetricDiff> toList() {
        return asList(firstContentfulPaint, speedIndex, largestContentfulPaint,
                interactive, totalBlockingTime, cumulativeLayoutShift,
                serverResponseTime);
    }

}
