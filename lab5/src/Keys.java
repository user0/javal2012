
public class Keys {


	public static int state=0;//ТЕКУЩЕЕ СОСТОЯНИЕ
	/*
	0 - ничего не нажато
	1 - нажата и держится клавиша open
	2 - нажата и держится клавиша close
	3 - ничего не нажато, окно автоматически открывается
	4 - ничего не нажато, окно автоматически закрывается

	*/
	
	synchronized void start_open() throws InterruptedException
	{

	if (state==0) 
		{
		state=1;
		System.out.println("\nОкно открывается\n");
		return;
		}
	if (state==1)
		{
		System.out.println("\nERROR\n");
		return;
		}
	if (state==2) 
		{
		state=1; //приоритет open
		System.out.println("\nОкно открывается\n");
		return;
		}
	if (state==3) 
		{
		System.out.println("\nОкно уже окрывается\n");
		return;
		}
	if (state==4)
		{
		state=1;
		System.out.println("\nОкно открывается\n");
		return;
		}
	}


	synchronized void stop_open() throws InterruptedException
	{
			
	if (state==1)
		{
		state=0; 
		//System.out.println("\nОстановлено открытие\n");
		}
	}


	synchronized void start_close() throws InterruptedException
	{

	if (state==0) 
		{
		state=2;
		System.out.println("\nОкно закрывается\n");
		return;
		}
	if (state==1)
		{
		System.out.println("\nПриоритет окрытию\n");
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
		System.out.println("\nОкно закрывается\n");
		return;
		}
	if (state==4) 
		{
		System.out.println("\nУже закрывается\n");
		return;
		}
	}


	synchronized void stop_close() throws InterruptedException
	{

	if (state==2)
		{
		state=0; 
		//System.out.println("\nОстановлено закрытие\n");
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
								//Симулируем нажатие клавиш
								System.out.println("\nНажимаем кнопку открытия - start_open\n");
								KeysK.start_open();
								Thread.sleep(8000);
								System.out.println("\nОтпускаем кнопку открытия - stop_open\n");
								KeysK.stop_open();
								Thread.sleep(6000);
								System.out.println("\nНажимаем кнопку открытия - start_open\n");
								KeysK.start_open();
								Thread.sleep(2000);	//Быстрое нажатие open
								System.out.println("\nОтпускаем кнопку открытия - stop_open\n");
								KeysK.stop_open();
								Thread.sleep(14000);
								System.out.println("\nНажимаем кнопку закрытия - start_close\n");
								KeysK.start_close();
								Thread.sleep(2000); //Быстрое нажатие close
								System.out.println("\nОтпускаем кнопку закрытия - stop_close\n");
								KeysK.stop_close();
								Thread.sleep(8000);
								System.out.println("\nНажимаем кнопку открытия - start_open\n");
								KeysK.start_open();
								Thread.sleep(3000);
								System.out.println("\nОтпускаем кнопку открытия - stop_open\n");
								KeysK.stop_open();
								
								/*
								
								
								System.out.println("\nНажимаем кнопку открытия - start_open\n");
								KeysK.start_open();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(8000);
								System.out.println("\nОтпускаем кнопку открытия - stop_open\n");
								KeysK.stop_open();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(3000);
								System.out.println("\nНажимаем кнопку закрытия - start_close\n");
								KeysK.start_close();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(1000);
								System.out.println("\nОтпускаем кнопку закрытия - stop_close\n");
								KeysK.stop_close();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(5000);
								System.out.println("\nНажимаем кнопку открытия - start_open\n");
								KeysK.start_open();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(2000);
								System.out.println("\nОтпускаем кнопку открытия - stop_open\n");
								KeysK.stop_open();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(23000);
								System.out.println("\nНажимаем кнопку закрытия - start_close\n");
								KeysK.start_close();
								System.out.println("\nСостояние после нажатия - state="+Keys.state);
								Thread.sleep(1000);
								System.out.println("\nОтпускаем кнопку закрытия - stop_close\n");
								KeysK.stop_close();
								Thread.sleep(5000);
								System.out.println("\nНажимаем кнопку открытия - start_open\n");
								KeysK.start_open();
								Thread.sleep(3000);
								System.out.println("\nОтпускаем кнопку открытия - stop_open\n");
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
