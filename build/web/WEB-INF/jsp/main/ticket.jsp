<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
Ticket ID: ${ticketObject.ticketId}
<br />
Ticket Description: ${ticketObject.shortDescription}
<br /><br />

Ticket Type ${ticketObject.applicationControl.ticketType.ticketTypeName}
<br />
Severity: ${ticketObject.applicationControl.severity.severityLevel} : ${ticketObject.applicationControl.severity.severityName}
<br />
Application: ${ticketObject.applicationControl.application.applicationName}
<br />
Workflow: ${ticketObject.applicationControl.workflow.workflowName}


<br /><br /><br />
Created By: ${ticketObject.createdBy.firstName} ${ticketObject.createdBy.lastName}
<br />
Reported By: ${ticketObject.reportedBy.firstName} ${ticketObject.reportedBy.lastName}
<br />
Processing By: ${ticketObject.messageProcessor.firstName} ${ticketObject.messageProcessor.lastName}
