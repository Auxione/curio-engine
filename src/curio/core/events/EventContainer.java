package core.events;

import java.util.ArrayList;

/**
 * Event container interface for curio-engine.
 * 
 * @author Mehmet Cem Zarifoglu
 *
 */
public interface EventContainer {

	public void registerEvent(Event event);

	public ArrayList<Event> getEvents();
}
