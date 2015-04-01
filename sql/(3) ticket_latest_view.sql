SELECT ticket.ticket_id, short_description, reported_by_user_id, ticket_type_id, application_id, severity_id, workflow_id, workflow_status_id, requestor_user_id, stamp 
    FROM ticket 
    JOIN ticket_control_latest_view 
        ON ticket.ticket_id=ticket_control_latest_view.ticket_id