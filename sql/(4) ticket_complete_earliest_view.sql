SELECT ticket_id, short_description, reported_by_user_id, ticket_type_id, 
application_id, severity_id, ticket_earliest_view.workflow_id, workflow_status_id, queue_id, requestor_user_id, stamp 
FROM workflow_structure 
JOIN ticket_earliest_view
   ON workflow_structure.workflow_id=ticket_earliest_view.workflow_id 
        AND 
      workflow_structure.from_workflow_status_id=ticket_earliest_view.workflow_status_id