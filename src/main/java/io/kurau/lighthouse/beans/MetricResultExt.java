package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter@Setter
@Accessors(chain = true)
public class MetricResultExt {

    private String title;
    private String href = "";
    private long size;

    private double min;
    private double max;
    private double med;

    public static MetricResultExt metricResult() {
        return new MetricResultExt();
    }
}
