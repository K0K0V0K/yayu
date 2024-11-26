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
        <#list rms as rm>
            <div class="column is-half">
                ${rm}
            </div>
        </#list>
        </div>
    </div>
</div>
</body>
</html>