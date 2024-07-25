package com.yang.gatherexclusive.repository;

import com.yang.gatherexclusive.entity.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:mysql://localhost:3306/gather_exclusive?createDatabaseIfNotExist=true"
})
public class EventInviteeRepositoryTest {
    @Autowired
    private EventInviteeRepository eventInviteeRepository;

    @Test
    public void testFindByEventAndInvitee() {
        Event event = new Event();
        event.setEventName("name");
        event.setEventType("type");
        LocalDateTime time = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        event.setEventTime(time);
        event.setEventLocation("somewhere");
        event.setEventDescription("come have fun");


//        assertEquals()
    }
}
