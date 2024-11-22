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
        <div class="table-container">
            <table class="table">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Last Modified</th>
                    <th>Size</th>
                </tr>
                </thead>
                <tbody>
                ${props}
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>