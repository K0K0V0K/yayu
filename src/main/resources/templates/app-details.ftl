<!DOCTYPE html>
<html>
<head>
    <title>YAYU</title>
    <link href="/bulma.min.css" rel="stylesheet">
</head>
<body>
<#include "nav-bar.ftl">

<div class="columns">
    <div class="column is-4 is-offset-1">
        ${info}
    </div>
    <div class="column is-6">
        ${capacity}
    </div>
</div>

<div class="columns">
    <div class="column is-10 is-offset-1">
        <article class="message">
            <div class="message-header">
                <p>Diagnostic message</p>
            </div>
            <div class="message-body">
                ${props.diagnostics}
            </div>
        </article>
    </div>
</div>


<div class="columns">
    <div class="column is-10  is-offset-1">
        <article class="panel is-danger">
            <p class="panel-heading">Attempts</p>
            <a class="panel-block">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Attempt Id</th>
                        <th>State</th>
                        <th>Container Id</th>
                        <th>Start</th>
                        <th>Finish</th>
                    </tr>
                    </thead>
                    <tbody>
                    ${attempts}
<#--                    <#list attempts as attempt>-->
<#--                        <tr>-->
<#--                            <td>${attempt.id}</td>-->
<#--                            <td>${attempt.appAttemptState}</td>-->
<#--                            <td>${attempt.containerId}</td>-->
<#--                            <td>${time(attempt.startTime)}</td>-->
<#--                            <td>${time(attempt.finishedTime)}</td>-->
<#--                        </tr>-->
<#--                    </#list>-->
                    </tbody>
                </table>
            </a>
        </article>
    </div>
</div>


<#list attempts as attempt>
    ${attempt}
</#list>

<div class="columns">
    <div class="column is-10 is-offset-1">
        <div class="table-container">
            <table class="table">
                <thead>
                <tr>
                    <th> Property Name</th>
                    <th> Property Value</th>
                </tr>
                </thead>
                <tbody>
                ${details(props)}
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>