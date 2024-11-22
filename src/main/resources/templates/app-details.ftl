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
        <article class="panel is-link">
            <p class="panel-heading">Info - ${props.id}</p>
            <a class="panel-block">
                Application Type: ${props.applicationType}
            </a>
            <a class="panel-block">
                Name: ${props.name}
            </a>
            <a class="panel-block">
                Application Tags: ${props.applicationTags}
            </a>
            <a class="panel-block">
                Queue: ${props.queue}
            </a>
            <a class="panel-block">
                User: ${props.user}
            </a>
            <a class="panel-block">
                State: ${props.state}
            </a>
            <a class="panel-block">
                Final Status: ${props.finalStatus}
            </a>
            <a class="panel-block">
                Start: ${time(props.startedTime)}
            </a>
            <a class="panel-block">
                Finish: ${time(props.finishedTime)}
            </a>
        </article>
    </div>
    <div class="column is-6">
        <article class="panel is-info">
            <p class="panel-heading">Capacity</p>
            <a class="panel-block">
                Allocated Memory: ${props.allocatedMB} MB
            </a>
            <a class="panel-block">
                Allocated vCores: ${props.allocatedVCores}
            </a>
            <a class="panel-block">
                Memory seconds: ${props.memorySeconds}
            </a>
            <a class="panel-block">
                vCore seconds: ${props.vcoreSeconds}
            </a>
            <a class="panel-block">
                Preempted VCores: ${props.preemptedResourceVCores}
            </a>
            <a class="panel-block">
                Preempted Memory: ${props.preemptedResourceMB} MB
            </a>
            <a class="panel-block">
                Preempted vCore Seconds: ${props.preemptedVcoreSeconds}
            </a>
            <a class="panel-block">
                Preempted Memory Seconds: ${props.preemptedMemorySeconds}
            </a>
            <a class="panel-block">
                Priority: ${props.priority}
            </a>
        </article>
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
                    <#list attempts as attempt>
                        <tr>
                            <td>${attempt.id}</td>
                            <td>${attempt.appAttemptState}</td>
                            <td>${attempt.containerId}</td>
                            <td>${time(attempt.startTime)}</td>
                            <td>${time(attempt.finishedTime)}</td>
                        </tr>
                    </#list>
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