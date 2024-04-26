<%@include file="/WEB-INF/learnx/top.jsp"%>
<div class="container mt-5">
  <div class="row justify-content-center">
    <div class="col-md-6">
      <h2>Chat</h2>
      <form id="messageForm">
        <div class="form-group">
          <label for="userName">Your Name</label>
          <input type="text" class="form-control" id="userName">
        </div>
        <div class="form-group">
          <label for="message">Message</label>
          <textarea class="form-control" id="message" rows="5"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Send</button>
      </form>
      <!-- Error Notification Placeholder -->
      <div id="errorText" class="alert alert-danger d-none" role="alert"></div>
      <!-- Message Placeholder -->
      <div id="messages"></div>
    </div>
  </div>
</div>
<%@include file="/WEB-INF/learnx/bottom.jsp"%>