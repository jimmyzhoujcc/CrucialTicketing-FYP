SELECT * FROM ticket_change_log 
WHERE (ticket_id,stamp) 
    IN ( 
        SELECT ticket_change_log.ticket_id, MAX(ticket_change_log.stamp)
        FROM ticket_change_log
        GROUP BY ticket_change_log.ticket_id
    ) 
ORDER BY ticket_id, stamp