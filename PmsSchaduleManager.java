import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PmsSchaduleManager implements ScheduleManager{
	private ScheduleEvent sEvent;
	private List<ScheduleEvent> []listArray;
	public ScheduleEvent getScheduleEvent()
	{
		return sEvent;
	}
	public void setScheduleEvent(LocalDateTime date)
	{
		List<ScheduleEvent> list1 = this.getEventsByDate(date);
		for(ScheduleEvent ev: list1)
		{
			if(date.getDayOfWeek().getValue() == ev.getStartTime().getDayOfWeek().getValue())
			{
				sEvent = ev;
				return;
			}
		}
	}
	//이벤트를 추가하는 함수
	@Override
	public void addEvent(String title, String description, LocalDateTime start, LocalDateTime end) {
		// TODO Auto-generated method stub
		sEvent = new ScheduleEvent(title,description,start,end);
		//중복여부 파악
		listArray[start.getMonth().getValue()].add(sEvent);
		
		
	}
//그 달의 이벤트를 찾아서 리턴하는 함수
	@Override
	public List<ScheduleEvent> getEventsByDate(LocalDateTime date) {
		// TODO Auto-generated method stub
		int i=0;
		 for (List<ScheduleEvent> list : listArray) {
			 
		        if (i++ == date.getMonth().getValue()) {
		            return list; // 동일한 시작 시간이 있음
		        }
		        else
		        	continue;
		        
		    }
		  
		return null;
	}
	
	//이벤트를 찾아서 제거하는 함수
	

}
