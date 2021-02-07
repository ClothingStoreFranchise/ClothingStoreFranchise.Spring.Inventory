package clothingstorefranchise.spring.inventory.facade.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import clothingstorefranchise.spring.common.constants.EventState;
import clothingstorefranchise.spring.common.event.IIntegrationEventLogService;
import clothingstorefranchise.spring.common.event.IntegrationEvent;
import clothingstorefranchise.spring.inventory.model.IntegrationEventLog;
import clothingstorefranchise.spring.inventory.repositories.IIntegrationEventRepository;

@Service
public class IntegrationEventLogService implements IIntegrationEventLogService {
	
	@Autowired
	private IIntegrationEventRepository inEventRepository;
	
	public void saveEvent(IntegrationEvent event) {
		IntegrationEventLog eventLog = new IntegrationEventLog(event);

		inEventRepository.save(eventLog);
	}
	
	public void putEventAsPublished(UUID eventId) {
		updateEventStatus(eventId, EventState.PUBLISHED);
	}
	
	public void putEventAsNoPublished(UUID eventId) {
		updateEventStatus(eventId, EventState.NO_PUBLISHED);
	}
	
	public void putEventAsInProgress(UUID eventId) {
		updateEventStatus(eventId, EventState.IN_PROGRESS);
	}
	
	public void putEventAsFailed(UUID eventId) {
		updateEventStatus(eventId, EventState.PUBLISHED_FAILED);
	}
	
	public List<IntegrationEventLog> retrieveEventsPendingToPublish(UUID eventId) {
		 return inEventRepository.findByEventIdAndStateOrderByCreationTimeDesc(eventId, EventState.NO_PUBLISHED);
	}
	
	private void updateEventStatus(UUID eventId, int state) {
		var eventLog = inEventRepository.findById(eventId).get();
		eventLog.setState(state);
		
		if(state == EventState.IN_PROGRESS) {
			int timesSent = eventLog.getTimesSent() + 1;
			eventLog.setTimesSent(timesSent);
		}
		
		inEventRepository.save(eventLog);
	}
}
