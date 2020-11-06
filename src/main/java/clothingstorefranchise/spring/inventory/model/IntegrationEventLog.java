package clothingstorefranchise.spring.inventory.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;

import clothingstorefranchise.spring.common.constants.EventState;
import clothingstorefranchise.spring.common.event.IntegrationEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IntegrationEventLog {
	@Id
    @EqualsAndHashCode.Include
    @Column(insertable = true)
	@Type(type="uuid-char")
    private UUID eventId;
	@NotNull
	@Column
    private String content;
    @NotNull
    @Column
    private LocalDateTime creationTime;
    @NotNull
    @Setter
    @Column
    private int state;
    @Setter
    @Column(nullable = false)
    private int timesSent;
    
    private String eventName;
    
    public IntegrationEventLog(IntegrationEvent event) {
    	Gson gson = new Gson();
    	
		this.eventId = event.getEventId();
		this.content = gson.toJson(event);
		this.creationTime = LocalDateTime.parse(event.getCreationDate(), DateTimeFormatter.ISO_DATE_TIME);
		this.state = EventState.NO_PUBLISHED;
		this.timesSent = 0;
		this.eventName = event.getClass().getSimpleName();
	}
    
    public IntegrationEventLog() { }
}
