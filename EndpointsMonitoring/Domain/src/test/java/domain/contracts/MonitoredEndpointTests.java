package domain.contracts;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonitoredEndpointTests {
    @Test
    public void create_monitoredIntervalGreaterThanZero_success() {
        var creationDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        new MonitoredEndpoint("name", "url", creationDateTime, 1, null);
    }

    @Test
    public void create_monitoredIntervalIsEqualToZero_exception() {
        var creationDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        var exception = assertThrows(IllegalArgumentException.class, () -> new MonitoredEndpoint("name", "url", creationDateTime, 0, null));
        assertEquals(exception.getMessage(), "monitoredInterval should be integer greater than 0");
    }

    @Test
    public void create_monitoredIntervalIsNegative_exception() {
        var creationDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        var exception = assertThrows(IllegalArgumentException.class, () -> new MonitoredEndpoint("name", "url", creationDateTime, -4, null));
        assertEquals(exception.getMessage(), "monitoredInterval should be integer greater than 0");
    }

    @Test
    public void edit_monitoredIntervalGreaterThanZero_originalEntityNotChangedNewCreated() {
        var creationDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        var owner = new User("username", "email", "token");
        var original = new MonitoredEndpoint("name", "url", creationDateTime, 1, owner);
        var edited = original.edit("new name", "new url", 2);

        assertEquals("name", original.getName());
        assertEquals("url", original.getUrl());
        assertEquals(creationDateTime, original.getDateOfCreation());
        assertEquals(1, original.getMonitoredInterval());
        assertEquals(owner, original.getOwner());

        assertEquals("new name", edited.getName());
        assertEquals("new url", edited.getUrl());
        assertEquals(creationDateTime, edited.getDateOfCreation());
        assertEquals(2, edited.getMonitoredInterval());
        assertEquals(owner, edited.getOwner());
    }

    @Test
    public void edit_monitoredIntervalIsEqualToZero_exception() {
        var creationDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        var original = new MonitoredEndpoint("name", "url", creationDateTime, 1, null);
        var exception = assertThrows(IllegalArgumentException.class, () -> original.edit("new name", "new url", 0));
        assertEquals(exception.getMessage(), "monitoredInterval should be integer greater than 0");
    }

    @Test
    public void edit_monitoredIntervalIsNegative_exception() {
        var creationDateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        var original = new MonitoredEndpoint("name", "url", creationDateTime, 1, null);
        var exception = assertThrows(IllegalArgumentException.class, () -> original.edit("new name", "new url", -7));
        assertEquals(exception.getMessage(), "monitoredInterval should be integer greater than 0");
    }
}
