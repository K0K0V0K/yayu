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
        <article class="panel is-primary">
            <p class="panel-heading">Config</p>
            <a class="panel-block">
                <div class="content is-clipped">
                    <ul>
                        <#list props as config>
                            <li>${config.name} = ${config.value?c} <span class="tag"> ${config.source} </span></li>
                        </#list>
                    </ul>
                </div>

            </a>
        </article>
    </div>
</div>
</body>
</html>