<%@include file="/WEB-INF/shared/top.jsp"%>
<title>Title</title>
<link href="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.css" rel="stylesheet"/>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/nprogress@0.2.0/nprogress.js"></script>
<link rel="stylesheet" href="css/loading.css">
<script src="js/loading.js"></script>
</head>
<body>
<h1>${artist.name}</h1>
<img src="${artist.picture}">
<%@include file="/WEB-INF/shared/bottom.jsp"%>
