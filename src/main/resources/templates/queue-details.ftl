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
        <div class="columns">
            <div class="column is-4">
                ${am}
                ${acls}
            </div>
            <div class="column is-4">
                ${capacity}
                ${state}
            </div>
            <div class="column is-4">
                ${config}
            </div>
        </div>
    </div>
</div>
</body>
</html>