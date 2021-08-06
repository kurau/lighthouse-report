package io.kurau.lighthouse.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter@Setter
@Accessors(chain = true)
public class MetricDiff {

    private long actualMed;
    private long toCompareMed;
    private long diffMed;
    private String title;
    private String color = "background-color: #ff9c9c;";
    private boolean important = false;

    public MetricDiff print() {
        System.out.println(String.format("%s, %s, %s, %s, %s", actualMed, toCompareMed, diffMed, title, color));
        return this;
    }

    public MetricDiffExt toExt() {
        return new MetricDiffExt()
                .setActualMed((double) actualMed/1000)
                .setToCompareMed((double) toCompareMed/1000)
                .setDiffMed((double) diffMed/1000)
                .setTitle(title).setColor(color).setImportant(important);
    }
}
