package io.kurau.lighthouse;

import com.google.gson.Gson;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.kurau.lighthouse.beans.LightHouseResult;
import io.kurau.lighthouse.beans.ResultsContainer;
import org.apache.commons.io.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static io.kurau.lighthouse.utils.MetricUtil.createResult;
import static io.kurau.lighthouse.utils.MetricUtil.median;
import static io.kurau.lighthouse.utils.Util.getResourceAsString;
import static io.kurau.lighthouse.utils.Util.toLong;
import static java.lang.Double.parseDouble;
import static java.util.stream.Collectors.toList;

public class Main {

    private static List<LightHouseResult> testResults = new ArrayList<>();
    private static List<LightHouseResult> prodResults = new ArrayList<>();

    private static ResultsContainer test = new ResultsContainer();
    private static ResultsContainer prod = new ResultsContainer();

    private static List<Long> firstContentfulPaint;
    private static List<Long> speedIndex;
    private static List<Long> largestContentfulPaint;
    private static List<Long> interactive;
    private static List<Long> totalBlockingTime;
    private static List<Long> cumulativeLayoutShift;
    private static List<Long> serverResponseTime;

    private static Map<String, Object> root = new HashMap<>();

    public static void main(String[] args) throws IOException, TemplateException {
        Files.walk(Paths.get("."))
                .filter(f -> f.toAbsolutePath().toString().contains("test"))
                .filter(f -> f.toAbsolutePath().toString().contains("lighthouse.json"))
                .forEach(f -> testResults.add(
                        new Gson().fromJson(getResourceAsString(f.toAbsolutePath().toString()),
                                LightHouseResult.class)));

        Files.walk(Paths.get("."))
                .filter(f -> f.toAbsolutePath().toString().contains("prod"))
                .filter(f -> f.toAbsolutePath().toString().contains("lighthouse.json"))
                .forEach(f -> prodResults.add(
                        new Gson().fromJson(getResourceAsString(f.toAbsolutePath().toString()),
                                LightHouseResult.class)));

        aggregate(testResults, test);
        aggregate(prodResults, prod);

        List<Long> testScore = testResults.stream()
                .map(r -> (long) (r.getCategories().getPerformance().getScore() * 100)).collect(toList());
        List<Long> prodScore = prodResults.stream()
                .map(r -> (long) (r.getCategories().getPerformance().getScore() * 100)).collect(toList());

        root.put("name", "Freemarker");
        root.put("cookie", System.getenv("COOKIE"));
        root.put("url", testResults.get(0).getFinalUrl());
        root.put("test", test.toList());
        root.put("prod", prod.toList());
        root.put("testScore", median(testScore));
        root.put("prodScore", median(prodScore));
        root.put("diff", test.diff(prod).toList());

        writeResult();
        copyResources();
    }

    private static void writeResult() throws IOException {
        Template temp = freeMarkerCfg().getTemplate("lh_common.ftlh");
        try {
            Writer out = new FileWriter("project/lh.html");
            temp.process(root, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyResources() {
        try {
            Files.copy(Paths.get("templates/index.min.css"), Paths.get("project/index.min.css"));
        } catch (FileAlreadyExistsException e) {
            System.out.println(" index.min.css already exist ");
        } catch (IOException e) {
            System.out.println(" index.min.css fail to copy ");
            e.printStackTrace();
        }
    }

    private static void aggregate(List<LightHouseResult> results, ResultsContainer container) {
        firstContentfulPaint = extract(results, r -> r.getAudits().getFirstContentfulPaint().getNumericValue());
        speedIndex = extract(results, r -> r.getAudits().getSpeedIndex().getNumericValue());
        largestContentfulPaint = extract(results, r -> r.getAudits().getLargestContentfulPaint().getNumericValue());
        interactive = extract(results, r -> r.getAudits().getInteractive().getNumericValue());
        totalBlockingTime = extract(results, r -> r.getAudits().getTotalBlockingTime().getNumericValue());
        cumulativeLayoutShift = extract(results, r ->
                (parseDouble(r.getAudits().getCumulativeLayoutShift().getNumericValue())*100)+"");
        serverResponseTime = extract(results, r -> r.getAudits().getServerResponseTime().getNumericValue());

        container.setFirstContentfulPaint(createResult(firstContentfulPaint)
                .setTitle("first-contentful-paint")
                .setHref("https://web.dev/first-contentful-paint/"));
        container.setSpeedIndex(createResult(speedIndex)
                .setTitle("speed-index")
                .setHref("https://web.dev/speed-index/"));
        container.setLargestContentfulPaint(createResult(largestContentfulPaint)
                .setTitle("largest-contentful-paint")
                .setHref("https://web.dev/lcp/"));
        container.setInteractive(createResult(interactive)
                .setTitle("Time to Interactive")
                .setHref("https://web.dev/interactive/"));
        container.setTotalBlockingTime(createResult(totalBlockingTime)
                .setTitle("total-blocking-time")
                .setHref("https://web.dev/lighthouse-total-blocking-time/"));
        container.setCumulativeLayoutShift(createResult(cumulativeLayoutShift)
                .setTitle("cumulative-layout-shift")
                .setHref("https://web.dev/cls/"));
        container.setServerResponseTime(createResult(serverResponseTime)
                .setTitle("(TTFB) server-response-time")
                .setHref("https://web.dev/time-to-first-byte/"));
    }

    private static List<Long> extract(List<LightHouseResult> results, Function<LightHouseResult, String> function) {
        return results.stream()
                .filter(r -> Optional.ofNullable(function.apply(r)).isPresent())
                .map(r -> toLong(function.apply(r)))
                .collect(toList());
    }

    private static Configuration freeMarkerCfg() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setTemplateLoader(new ClassTemplateLoader(Main.class.getClassLoader(), "templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }
}
