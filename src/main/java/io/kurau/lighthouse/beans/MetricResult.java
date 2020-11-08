package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter@Setter
@Accessors(chain = true)
public class MetricResult {

    private String title;
    private String href = "";
    private long size;

    private long min;
    private long max;
    private long med;

    public static MetricResult metricResult() {
        return new MetricResult();
    }
}
