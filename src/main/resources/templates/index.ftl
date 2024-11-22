<!DOCTYPE html>
<html>
<head>
    <title>YAYU</title>
    <link href="/bulma.min.css" rel="stylesheet">
</head>
<body>
<#include "nav-bar.ftl">
<div class="columns">
    <div class="column is-1"></div>
    <#list counts as state, count>
    <div class="column is-1">${appState(state)} ${count}</div>
    </#list>
</div>
<div class="columns">
    <div class="column is-10 is-offset-1">
        <div class="table-container">
            <table class="table">
                <thead>
                <tr>
                    ${tableHead("id", "applicationType", "name", "user", "state", "queue", "progress", "startedTime")}
                </tr>
                </thead>
                <tbody>
                <#list apps as app>
                    <tr>
                        <td>${linkTag("app", app.id)}</td>
                        <td>${app.applicationType}</td>
                        <td>${app.name}</td>
                        <td>${app.user}</td>
                        <td>${appState(app.state)}</td>
                        <td>${app.queue}</td>
                        <td>${progress(app.progress)}</td>
                        <td>${time(app.startedTime)}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>