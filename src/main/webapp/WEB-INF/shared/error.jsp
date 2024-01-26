<%@include file="/WEB-INF/shared/top.jsp"%>
    <title>${pageTitle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-12 text-center">
            <h2>Oh no!</h2>
            <p class="mb-4">Either something went wrong or this page doesn't exist anymore.</p>
            <a href="${appURL}" class="btn btn-outline-primary">Home page</a>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/shared/bottom.jsp"%>
