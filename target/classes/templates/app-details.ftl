<!DOCTYPE html>
<html>
<head>
    <title>YAYU</title>
    <link href="/bulma.min.css" rel="stylesheet">
</head>
<body>
    <#include "nav-bar.ftl">
    <table class="table" style="margin-left: 1em;">
      <thead>
        <tr>
          <th> Property Name </th>
          <th> Property Value </th>
        </tr>
      </thead>
      <tbody>
        ${details(app)}
      </tbody>
    </table>
</body>
</html>