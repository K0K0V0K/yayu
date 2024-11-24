<!DOCTYPE html>
<html>
<head>
    <title>YAYU</title>
    <link href="/bulma.min.css" rel="stylesheet">
</head>
<body>
<#include "nav-bar.ftl">
<div class="columns">
    <div class="column is-10 is-offset-1">
        <article class="message">
            <div class="message-header">
                <p>Application Statistics</p>
            </div>
            <div class="message-body">
                <div class="columns">
                    <#list counts as state, count>
                        <div class="column is-2">
                            ${appState(state)}
                            <span class="tag is-black">${count}</span>
                        </div>
                    </#list>
                </div>
            </div>
        </article>
        <div class="columns">
            <div class="column">
                ${topUser}
            </div>
            <div class="column">
                ${topQueue}
            </div>
            <div class="column">
                ${topAppType}
            </div>
        </div>
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