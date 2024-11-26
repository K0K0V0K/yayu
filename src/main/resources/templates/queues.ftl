<!DOCTYPE html>
<html>
<head>
    <title>YAYU</title>
    <link href="/bulma.min.css" rel="stylesheet">
</head>
<body>
<#include "nav-bar.ftl">

<#list queues as level, lists>
    <div class="columns">
        <div class="column is-10 is-offset-1">
            <article class="panel is-primary">
                <p class="panel-heading">Queues - ${level}</p>
                <a class="panel-block">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>Queue</th>
                            <th>Absolute Used Capacity</th>
                            <th>Absolute Capacity</th>
                            <th>Absolute Max Capacity</th>
                            <th>Configured Capacity</th>
                            <th>Configured Max Capacity</th>
                            <th>Children</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list lists as queue>
                            <tr>
                                <td>${linkTag("queue", queue.queuePath)}</td>
                                <td>${queue.absoluteUsedCapacity}</td>
                                <td>${queue.absoluteCapacity}</td>
                                <td>${queue.absoluteMaxCapacity}</td>
                                <td>${queue.capacity}</td>
                                <td>${queue.maxCapacity}</td>
                                <td>${queue.children}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </a>
            </article>
        </div>
    </div>
</#list>
</body>
</html>