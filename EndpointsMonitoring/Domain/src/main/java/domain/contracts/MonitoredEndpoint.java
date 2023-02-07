package domain.contracts;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
public class MonitoredEndpoint {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    //todo: change type to UUID
    private String id;
    private String name;
    private String url;
    private LocalDateTime dateOfCreation;
    private int monitoredInterval;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "owner_id")
    private User owner;

    public MonitoredEndpoint(String name, String url, LocalDateTime dateOfCreation, int monitoredInterval, User owner) {
        if (monitoredInterval <= 0) {
            throw new IllegalArgumentException("monitoredInterval should be integer greater than 0");
        }
        this.name = name;
        this.url = url;
        this.dateOfCreation = dateOfCreation;
        this.monitoredInterval = monitoredInterval;
        this.owner = owner;
    }

    private MonitoredEndpoint(String id, String name, String url, LocalDateTime dateOfCreation, int monitoredInterval, User owner) {
        this(name, url, dateOfCreation, monitoredInterval, owner);
        this.id = id;
    }

    public MonitoredEndpoint edit(String name, String url, int monitoredInterval) {
        return new MonitoredEndpoint(this.getId(), name, url, this.getDateOfCreation(), monitoredInterval, this.owner);
    }
}
