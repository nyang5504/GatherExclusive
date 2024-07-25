package com.yang.gatherexclusive.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:mysql://localhost:3306/gather_exclusive?createDatabaseIfNotExist=true"
})
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testFindByEventOrganizers_Organizer_Email(){

    }

    @Test
    public void testFindByEventInvitees_Invitee_EmailAndEventInvitees_Rsvped(){

    }
}
