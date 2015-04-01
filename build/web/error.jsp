<h1>Crucial Ticketing</h1>

<h3>An error has occurred with Crucial Manager</h3>
<h3>Please contact your local administrator quoting the error message below</h3>
<h4>Once reported, follow this <a href="<%=request.getContextPath()%>/home/login/login/">link</a> to try again</h4>

<h6>Notes below</h6>

<hr />
<br class="clearfix" />
${systemError}
<% request.getSession().removeAttribute("systemError"); %>