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
        ${healthRun}
    </div>
</div>

<div class="columns">
    <div class="column is-10  is-offset-1">
        ${healthInfo}
    </div>
</div>

<div class="columns">
    <div class="column is-10  is-offset-1">
        ${partitions}
    </div>
</div>

<div class="columns">
    <div class="column is-4  is-offset-1">
        ${capacityVector}
    </div>
    <div class="column is-6">
        ${acls}
    </div>
</div>
</body>
</html>