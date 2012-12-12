
public class Keys {


	public static int state=0;//������� ���������
	/*
	0 - ������ �� ������
	1 - ������ � �������� ������� open
	2 - ������ � �������� ������� close
	3 - ������ �� ������, ���� ������������� �����������
	4 - ������ �� ������, ���� ������������� �����������

	*/
	
	synchronized void start_open() throws InterruptedException
	{

	if (state==0) 
		{
		state=1;
		System.out.println("\n���� �����������\n");
		return;
		}
	if (state==1)
		{
		System.out.println("\nERROR\n");
		return;
		}
	if (state==2) 
		{
		state=1; //��������� open
		System.out.println("\n���� �����������\n");
		return;
		}
	if (state==3) 
		{
		System.out.println("\n���� ��� ����������\n");
		return;
		}
	if (state==4)
		{
		state=1;
		System.out.println("\n���� �����������\n");
		return;
		}
	}


	synchronized void stop_open() throws InterruptedException
	{
			
	if (state==1)
		{
		state=0; 
		//System.out.println("\n����������� ��������\n");
		}
	}


	synchronized void start_close() throws InterruptedException
	{

	if (state==0) 
		{
		state=2;
		System.out.println("\n���� �����������\n");
		return;
		}
	if (state==1)
		{
		System.out.println("\n��������� �������\n");
		return;
		}
	if (state==2) 
		{
		System.out.println("\nERROR\n");
		return;
		}
	if (state==3) 
		{
		state=2;
		System.out.println("\n���� �����������\n");
		return;
		}
	if (state==4) 
		{
		System.out.println("\n��� �����������\n");
		return;
		}
	}


	synchronized void stop_close() throws InterruptedException
	{

	if (state==2)
		{
		state=0; 
		//System.out.println("\n����������� ��������\n");
		}
	}
	
	
	
	
	public static void main(String[] args) {
		
		final Keys KeysK = new Keys();
		final Motor MotorM = new Motor();
		
		new Thread(
				new Runnable() {

					@Override
					public void run() {
						
							try {
								//���������� ������� ������
								System.out.println("\n�������� ������ �������� - start_open\n");
								KeysK.start_open();
								Thread.sleep(8000);
								System.out.println("\n��������� ������ �������� - stop_open\n");
								KeysK.stop_open();
								Thread.sleep(6000);
								System.out.println("\n�������� ������ �������� - start_open\n");
								KeysK.start_open();
								Thread.sleep(2000);	//������� ������� open
								System.out.println("\n��������� ������ �������� - stop_open\n");
								KeysK.stop_open();
								Thread.sleep(14000);
								System.out.println("\n�������� ������ �������� - start_close\n");
								KeysK.start_close();
								Thread.sleep(2000); //������� ������� close
								System.out.println("\n��������� ������ �������� - stop_close\n");
								KeysK.stop_close();
								Thread.sleep(8000);
								System.out.println("\n�������� ������ �������� - start_open\n");
								KeysK.start_open();
								Thread.sleep(3000);
								System.out.println("\n��������� ������ �������� - stop_open\n");
								KeysK.stop_open();
								
								/*
								
								
								System.out.println("\n�������� ������ �������� - start_open\n");
								KeysK.start_open();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(8000);
								System.out.println("\n��������� ������ �������� - stop_open\n");
								KeysK.stop_open();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(3000);
								System.out.println("\n�������� ������ �������� - start_close\n");
								KeysK.start_close();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(1000);
								System.out.println("\n��������� ������ �������� - stop_close\n");
								KeysK.stop_close();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(5000);
								System.out.println("\n�������� ������ �������� - start_open\n");
								KeysK.start_open();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(2000);
								System.out.println("\n��������� ������ �������� - stop_open\n");
								KeysK.stop_open();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(23000);
								System.out.println("\n�������� ������ �������� - start_close\n");
								KeysK.start_close();
								System.out.println("\n��������� ����� ������� - state="+Keys.state);
								Thread.sleep(1000);
								System.out.println("\n��������� ������ �������� - stop_close\n");
								KeysK.stop_close();
								Thread.sleep(5000);
								System.out.println("\n�������� ������ �������� - start_open\n");
								KeysK.start_open();
								Thread.sleep(3000);
								System.out.println("\n��������� ������ �������� - stop_open\n");
								*/
								}
							 catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							 }
						}
				}).start();
		
		
		new Thread(
				new Runnable() {

					@Override
					public void run() {

								while(!Thread.currentThread().isInterrupted()){
	
									try {
											
										synchronized(KeysK){
										MotorM.work();
										}
										Thread.sleep(1000);
										} 
									catch (InterruptedException e) {}
									}

					}
				}).start();
		
		

	}

}
