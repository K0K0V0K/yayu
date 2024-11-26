<nav class="navbar">
    <div class="navbar-brand">
        <figure class="image is-96x96 is-2by1">
            <img src="/logo.png" alt="Logo">
        </figure>
    </div>

    <div class="navbar-menu">
        <div class="navbar-start">
            <a class="navbar-item" href="/">
                Applications
            </a>
            <a class="navbar-item" href="/scheduler">
                Scheduler
            </a>
            <a class="navbar-item" href="/queues">
                Queues
            </a>
            <a class="navbar-item" href="/nodes">
                Nodes
            </a>
            <div class="navbar-item has-dropdown is-hoverable">
                <a class="navbar-link">
                    Tools
                </a>
                <div class="navbar-dropdown">
                    <a class="navbar-item" href="/config">
                        Configuration
                    </a>
                    <hr class="navbar-divider">
                    <a class="navbar-item" href="/logs">
                        Logs
                    </a>
                </div>
            </div>
        </div>

        <div class="navbar-end">
            <div class="navbar-item">
                <b>User:</b> ${user.requestedUser}
            </div>
            <div class="navbar-item">
                <b>Version:</b> ${clusterInfo.resourceManagerVersion}
            </div>
            <div class="navbar-item">
                <b>Started:</b> ${time(clusterInfo.startedOn)}
            </div>
            <div class="navbar-item">
                <#list haStatuses as url, state>
                    <a href="/cluster">${haState(url, state)}</a>
                </#list>
            </div>
        </div>
    </div>
</nav>