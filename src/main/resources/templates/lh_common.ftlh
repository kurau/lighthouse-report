<html>
<head>
    <link rel="stylesheet" href="index.min.css">
</head>
<body>

<div class="darkblue nav">
    <div class="navgrid">
        <ul>
            <li><a class="custom" href="#"> To infinity... and beyond! </a>/li>
            <li><a class="active" href="lh.html">Lighthouse</a></li>
        </ul>
    </div>
</div>

<div class="container">
    <h3 class="url">Median</h3>
    <h3 class="url">${url}</h3>
<#--    <h3 >${times} times</h3>-->
    <h3 >Cookie: ${cookie}</h3>
<#--    <h3 >Score Median: ${scoreMed}</h3>-->

    <div class="responsive">

        <table data-sortable="" id="detailed" data-sortable-initialized="true">
            <thead>
            <tr>
                <th>Compare</th>
                <th>med test</th>
                <th>med prod</th>
                <th>delta</th>
            </tr>
            </thead>
            <tbody>
            <#list diff as item_d>
                <tr>
                    <td data-title="Metric" data-value="${item_d.title}"><a href="">${item_d.title}</a></td>
                    <td data-title="med test" data-value="${item_d.actualMed}">${item_d.actualMed} ms</td>
                    <td data-title="med prod" data-value="${item_d.toCompareMed}">${item_d.toCompareMed} ms</td>
                    <td data-title="delta" style="${item_d.color}" data-value="${item_d.diffMed}">${item_d.diffMed} ms</td>
                </tr>
            </#list>

            </tbody>
        </table>

        <h3 >Score Median: ${testScore}</h3>
        <table data-sortable="" id="detailed" data-sortable-initialized="true">
            <thead>
            <tr>
                <th>Test</th>
                <th>min</th>
                <th>median</th>
                <th>max</th>
            </tr>
            </thead>
            <tbody>
            <#list test as item>
                <tr>
                    <td data-title="Metric" data-value="${item.title}"><a href="${item.href}">${item.title} - ${item.size}</a></td>
                    <td data-title="min" data-value="${item.min}">${item.min} ms</td>
                    <td data-title="median" data-value="${item.med}">${item.med} ms</td>
                    <td data-title="max" data-value="${item.max}">${item.max} ms</td>
                </tr>
            </#list>
            </tbody>
        </table>

        <h3 >Score Median: ${prodScore}</h3>
        <table data-sortable="" id="detailed" data-sortable-initialized="true">
            <thead>
            <tr>
                <th>Prod</th>
                <th>min</th>
                <th>median</th>
                <th>max</th>
            </tr>
            </thead>
            <tbody>
            <#list prod as item_p>
                <tr>
                    <td data-title="Metric" data-value="${item_p.title}"><a href="${item_p.href}">${item_p.title} - ${item_p.size}</a></td>
                    <td data-title="min" data-value="${item_p.min}">${item_p.min} ms</td>
                    <td data-title="median" data-value="${item_p.med}">${item_p.med} ms</td>
                    <td data-title="max" data-value="${item_p.max}">${item_p.max} ms</td>
                </tr>
            </#list>

            </tbody>
        </table>

    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="firstContentfulPaint.png" alt="">
        </div>
    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="speedIndex.png" alt="">
        </div>
    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="largestContentfulPaint.png" alt="">
        </div>
    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="timeToInteractive.png" alt="">
        </div>
    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="totalBlockingTime.png" alt="">
        </div>
    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="serverResponseTime.png" alt="">
        </div>
    </div>

    <div class="row">
        <div class="one columns">
            <img class="blue" src="score.png" alt="">
        </div>
    </div>
</div>

</body>
</html>
