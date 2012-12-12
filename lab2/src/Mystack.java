
public class Mystack implements Stack {
	private int n, cnt=0;
	private Object[] mem;
	private Object[] tmp;
	@Override
	public Object pop() {
		// TODO Auto-generated method stub
		if	(cnt>0){			
		cnt--;
		System.out.println("stack out element: " + mem[cnt]);
		return mem[cnt];}
		else
			System.out.println("Stack Null");
		return null;
	}

	@Override
	public void push(Object o) {
		// TODO Auto-generated method stub
		if(cnt%10 == 0) { 
			tmp = new Object[n]; 
			tmp = mem; 
			n += 10; 
			mem = new Object[n]; 
			for(int i = 0; i < n - 10;i++) { 
				mem[i] = tmp [i];
			}
			tmp = null; 
		}
		mem[cnt]=o;
		cnt++;
		
		System.out.println("stack add element: " + o);
		}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return cnt;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
Object hm[] = new Object[5];
			
		hm[0] = 12345;
		hm[1] = "ak47";
		hm[2] = "nivatron";
		hm[3] = "ggggggggg";
		hm[4] = 45;
		
		Mystack st = new Mystack();
		
		for(int i=0; i<3; i++){
			st.push(hm[i]);
		}
		st.pop();st.pop();
		for(int i=1; i<4; i++){
			st.push(hm[i]);
		}
		System.out.println("stack size: " + st.size());
	}
		
	

}
