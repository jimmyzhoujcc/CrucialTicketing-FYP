SELECT ticket_id, ticket_type_id, application_id, severity_id, workflow_id, workflow_status_id, requestor_user_id, stamp
FROM application_control 
    JOIN ticket_history_earliest_view
    ON application_control.application_control_id=ticket_history_earliest_view.application_control_id