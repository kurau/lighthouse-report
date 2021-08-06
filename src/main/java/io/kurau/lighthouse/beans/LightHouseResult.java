package io.kurau.lighthouse.beans;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LightHouseResult {

    private String finalUrl;
    private Audits audits;
    private Categories categories;

    @Getter@Setter
    public static class Audits {
        @SerializedName("first-contentful-paint")
        private Metric firstContentfulPaint;

        @SerializedName("speed-index")
        private Metric speedIndex;

        @SerializedName("largest-contentful-paint")
        private Metric largestContentfulPaint;

        private Metric interactive;

        @SerializedName("total-blocking-time")
        private Metric totalBlockingTime;

        @SerializedName("cumulative-layout-shift")
        private Metric cumulativeLayoutShift;

        @SerializedName("server-response-time")
        private Metric serverResponseTime;

        @SerializedName("server-timings")
        private ServerTimings serverTimings;
    }

    @Getter@Setter
    public static class Metric {
        private String id;
        private String title;
        private String numericValue;
        private String displayValue;
    }

    @Getter@Setter
    public static class Categories {
        private Performance performance;
        @Getter@Setter
        public static class Performance {
            public float score;
        }
    }

    @Getter@Setter
    public static class ServerTimings {
        private Timings details;
        @Getter@Setter
        public static class Timings {
            public JsonObject timings;
        }
    }
}
