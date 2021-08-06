package io.kurau.lighthouse.utils;

import io.kurau.lighthouse.beans.MetricResult;

import java.util.Collections;
import java.util.List;

import static java.lang.Math.toIntExact;

public class MetricUtil {

    public static int median(List<Long> sets){
        Collections.sort(sets);
        int med;
        int middle = sets.size()/2;
        if (sets.size()%2 == 1) {
            med = toIntExact(sets.get(middle));
        } else {
            med = toIntExact((sets.get(middle) + sets.get(middle - 1)) / 2);
        }
        return med;
    }

    public static MetricResult createResult(List<Long> list) {
        Collections.sort(list);
        MetricResult metricResult = MetricResult.metricResult().setSize(list.size());

        metricResult.setMin(list.get(0))
                .setMax(list.get(list.size()-1))
                .setMed(median(list));

        return metricResult;
    }
}
