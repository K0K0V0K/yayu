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
            <p class="panel-heading">Scheduler</p>
            <a class="panel-block">
                Capacity: ${props.capacity}
            </a>
            <a class="panel-block">
                Used capacity: ${props.usedCapacity}
            </a>
            <a class="panel-block">
                Max capacity: ${props.maxCapacity}
            </a>
            <a class="panel-block">
                Queue name: ${props.queueName}
            </a>
            <a class="panel-block">
                Last heath check: ${time(props.health.lastrun)}
            </a>
        </article>
    </div>
    <div class="column is-6">
        <article class="panel is-danger">
            <p class="panel-heading">Health - Last Run Details</p>
            <a class="panel-block">
                <table class="table" style="margin-left: 1em;">
                    <thead>
                    <tr>
                        <th>Operation</th>
                        <th>Count</th>
                        <th>Memory</th>
                        <th>vCores</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list details as element>
                        <tr>
                            <td>${element.operation}</td>
                            <td>${element.count}</td>
                            <td>${element.resources.memory}</td>
                            <td>${element.resources.vCores}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </a>
        </article>
    </div>
</div>

<div class="columns">
    <div class="column is-10  is-offset-1">
        <article class="panel is-danger">
            <p class="panel-heading">Health - Operation Info</p>
            <a class="panel-block">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Operation</th>
                        <th>Container Id</th>
                        <th>Node Id</th>
                        <th>Queue</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list health as element>
                        <tr>
                            <td>${element.operation}</td>
                            <td>${element.containerId}</td>
                            <td>${element.nodeId}</td>
                            <td>${element.queue}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </a>
        </article>
    </div>
</div>

<div class="columns">
    <div class="column is-10  is-offset-1">
        <article class="panel is-info">
            <p class="panel-heading">Partitions</p>
            <a class="panel-block">
                <table class="table" style="margin-left: 1em;">
                    <thead>
                    <tr>
                        <th>Partition Name</th>
                        <th>Capacity</th>
                        <th>Used Capacity</th>
                        <th>Max Capacity</th>
                        <th>Absolute Capacity</th>
                        <th>Absolute Used Capacity</th>
                        <th>Absolute Max Capacity</th>
                        <th>Max AM Limit Percentage</th>
                        <th>Weight</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list partitions as element>
                        <tr>
                            <td>${element.partitionName}</td>
                            <td>${element.capacity}</td>
                            <td>${element.usedCapacity}</td>
                            <td>${element.maxCapacity}</td>
                            <td>${element.absoluteCapacity}</td>
                            <td>${element.absoluteUsedCapacity}</td>
                            <td>${element.absoluteMaxCapacity}</td>
                            <td>${element.maxAMLimitPercentage}</td>
                            <td>${element.weight}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </a>
        </article>
    </div>
</div>

<div class="columns">
    <div class="column is-4  is-offset-1">
        <article class="panel is-success">
            <p class="panel-heading">Queue Capacity Vector Info</p>
            <a class="panel-block">
                <table class="table" style="margin-left: 1em;">
                    <thead>
                    <tr>
                        <th>Resource Name</th>
                        <th>Resource Value</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list queueCapacityVectorInfo as element>
                        <tr>
                            <td>${element.resourceName}</td>
                            <td>${element.resourceValue}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </a>
        </article>
    </div>
    <div class="column is-6">
        <article class="panel is-success">
            <p class="panel-heading">Queue Acls</p>
            <a class="panel-block">
                <table class="table" style="margin-left: 1em;">
                    <thead>
                    <tr>
                        <th>Access Type</th>
                        <th>Access Control List</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list queueAcls as element>
                        <tr>
                            <td>${element.accessType}</td>
                            <td>${element.accessControlList}</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </a>
        </article>
    </div>
</div>
</body>
</html>