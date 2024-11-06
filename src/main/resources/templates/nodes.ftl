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
            <table class="table" style="margin-left: 1em;">
              <thead>
                <tr>
                  ${tableHead("id", "state", "numContainers", "usedMemoryMB", "availMemoryMB", "usedVirtualCores", "availableVirtualCores", "lastHealthUpdate", "version")}
                  </tr>
                </thead>
                <tbody>
                  <#list nodes as node>
                    <tr>
                      <td>${linkTag("node", node.id)}</td>
                      <td>${node.state}</td>
                      <td>${node.numContainers}</td>
                      <td>${node.usedMemoryMB}</td>
                      <td>${node.availMemoryMB}</td>
                      <td>${node.usedVirtualCores}</td>
                      <td>${node.availableVirtualCores}</td>
                      <td>${time(node.lastHealthUpdate)}</td>
                      <td>${node.version}</td>
                     </tr>
                  </#list>
              </tbody>
            </table>
          </div>
        </div>
      </div>
</body>
</html>