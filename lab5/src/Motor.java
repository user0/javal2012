
public class Motor {

	public int timer = 0; //��� �������� ������� ���������. �������/��������
	public int position = 0; //������� ��������� ���� (0-�������)
	public final int T = 2; // ~ 2 �������
	public final int R = 20;//MAX
	
	public void work() throws InterruptedException
	{
		
		
		if (Keys.state==0)
		{
			//System.out.println("\n!!!!!!state = 0; timer="+timer);
			if ((Math.abs(timer)<=T)&(timer>0)) //�������� ������� ���������. �������
			{
				timer=0;
				Keys.state=3; 
				System.out.println("\n�������������� ��������\n");
			}
			if ((Math.abs(timer)<=T)&(timer<0))//�������� ������� ���������. ��������
			{
				timer=0;
				Keys.state=4; 
				System.out.println("\n�������������� ��������\n");
			}
			else
			{
				System.out.println("\n���� � ���������:"+position);	
				timer=0;
			}
			
		}
		
		
		
		if (Keys.state==1)
		{
			if (position==R)
			{
				Keys.state=0;
				timer=0;
				System.out.println("\n����� ������� �� �������\n");
			}
			else
			{
				position++;	//���������
				timer++;
				System.out.println("\n���� � ���������:"+position);	
			}
		}
		
		
		
		if (Keys.state==2)
		{
			if (position==0)
			{
				Keys.state=0;
				timer=0;
				System.out.println("\n����� ������� �� �������\n");
			}
			else
			{
				position--;	//���������
				timer--;
				System.out.println("\n���� � ���������:"+position);	
			}
		}
		
		
		
		
		if (Keys.state==3)
		{
			if (position==R)
			{
				Keys.state=0;
				//timer=0;
				System.out.println("\n����� ������� �� �������\n");
			}
			else
			{
				position++;	//���������
				System.out.println("\n���� � ���������:"+position);	
			}
		}
		
		
		
		
		if (Keys.state==4)
		{
			if (position==0)
			{
				Keys.state=0;
				//timer=0;
				System.out.println("\n����� ������� �� �������\n");
			}
			else
			{
				position--;	//���������
				System.out.println("\n���� � ���������:"+position);	
			}
		}
		
		


	}
	

}
