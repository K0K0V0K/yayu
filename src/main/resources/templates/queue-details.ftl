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
            <div class="column">
                ${am}
                ${acls}
            </div>
            <div class="column">
                ${capacity}
                ${state}
            </div>
            <div class="column">
                ${config}
            </div>
        </div>
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
        <div class="columns">
            <div class="column">
                <div>
                    ${table}
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>