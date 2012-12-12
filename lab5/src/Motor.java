
public class Motor {

	public int timer = 0; //для проверки условий автоматич. откытия/закрытия
	public int position = 0; //текущее положение окна (0-закрыто)
	public final int T = 2; // ~ 2 секунды
	public final int R = 20;//MAX
	
	public void work() throws InterruptedException
	{
		
		
		if (Keys.state==0)
		{
			//System.out.println("\n!!!!!!state = 0; timer="+timer);
			if ((Math.abs(timer)<=T)&(timer>0)) //проверка условий автоматич. откытия
			{
				timer=0;
				Keys.state=3; 
				System.out.println("\nАвтоматическое открытие\n");
			}
			if ((Math.abs(timer)<=T)&(timer<0))//проверка условий автоматич. закрытия
			{
				timer=0;
				Keys.state=4; 
				System.out.println("\nАвтоматическое закрытие\n");
			}
			else
			{
				System.out.println("\nОкно в положении:"+position);	
				timer=0;
			}
			
		}
		
		
		
		if (Keys.state==1)
		{
			if (position==R)
			{
				Keys.state=0;
				timer=0;
				System.out.println("\nОктно открыто до предела\n");
			}
			else
			{
				position++;	//ОТКРЫВАЕМ
				timer++;
				System.out.println("\nОкно в положении:"+position);	
			}
		}
		
		
		
		if (Keys.state==2)
		{
			if (position==0)
			{
				Keys.state=0;
				timer=0;
				System.out.println("\nОктно закрыто до предела\n");
			}
			else
			{
				position--;	//ЗАКРЫВАЕМ
				timer--;
				System.out.println("\nОкно в положении:"+position);	
			}
		}
		
		
		
		
		if (Keys.state==3)
		{
			if (position==R)
			{
				Keys.state=0;
				//timer=0;
				System.out.println("\nОктно открыто до предела\n");
			}
			else
			{
				position++;	//ОТКРЫВАЕМ
				System.out.println("\nОкно в положении:"+position);	
			}
		}
		
		
		
		
		if (Keys.state==4)
		{
			if (position==0)
			{
				Keys.state=0;
				//timer=0;
				System.out.println("\nОктно закрыто до предела\n");
			}
			else
			{
				position--;	//ЗАКРЫВАЕМ
				System.out.println("\nОкно в положении:"+position);	
			}
		}
		
		


	}
	

}
