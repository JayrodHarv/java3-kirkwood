<%@ include file="WEB-INF/shared/top.jsp"%>
    <title>Java 3 Web Applications</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.css" rel="stylesheet"/>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.js"></script>
    <link rel="stylesheet" href="css/loading.css">
    <script src="js/loading.js"></script>
</head>
<body>
<div class="container py-4 text-center">
    <h1 class="my-4">Java 3 Web Applications</h1>
    <div class="row justify-content-center" style="text-align: center;">
        <div class="col-xs-12 col-sm-6 col-lg-5 mb-4">
            <h3>Personal Projects</h3>
            <div class="list-group">
                <a href="${appURL}/smp" class="list-group-item list-group-item-action">SMP Website</a>
                <a href="${appURL}/email" class="list-group-item list-group-item-action">Email Thing</a>
            </div>
        </div>
        <div class="col-xs-12 col-sm-6 col-lg-5">
            <h3>Class Demos</h3>
            <div class="list-group">
                <a href="${appURL}/learnx" class="list-group-item list-group-item-action">LearnX Course Management System</a>
                <a href="${appURL}/all-users" class="list-group-item list-group-item-action">All Users</a>
                <a href="${appURL}/artist" class="list-group-item list-group-item-action">Artist From JSON</a>
            </div>
        </div>
    </div>
</div>
<%@ include file="WEB-INF/shared/bottom.jsp"%>