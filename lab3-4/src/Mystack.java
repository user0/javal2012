import java.util.Iterator;

public class Mystack<T> implements Stack<T>, Iterable<T> {
	private int n, cnt=0;
	private T[] mem;
	private T[] tmp;
	@Override
	public T pop() {
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
	public void push(T o) {
		// TODO Auto-generated method stub
		if(cnt%10 == 0) {
			tmp = (T[]) new Object[n]; 
			tmp = mem; 
			n += 10; 
			mem = (T[]) new Object[n]; 
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
		System.out.println("stack size: " + cnt);
		return cnt;
	}
	/**
	 * @param args
	 */
	
	public Iterator<T> iterator() {
	
	
	
	class Myiterator implements Iterator<T>{
		
		public boolean hasNext() {
			
			if(cnt == 0)  return false;		
			else
				return true;
			
	}

	@Override
	public T next() {
		
		if(cnt == 0) 
			return null;
			cnt--;
			return mem[cnt];
		
	}
	
	@Override
	public void remove() {
		if(hasNext()) {
			cnt--;
		}

	}

	}
	return new Myiterator();
}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
String hm[] = new String[5];
			
		hm[0] = "12345";
		hm[1] = "ak47";
		hm[2] = "nivatron";
		hm[3] = "ggggggggg";
		hm[4] = "45";
		
		Mystack<String> st = new Mystack<String>();
		
		for(int i=0; i<3; i++){
			st.push(hm[i]);
		}
		st.pop();st.pop();
		for(int i=2; i<4; i++){
			st.push(hm[i]);
		}
		st.size();
		
		Iterator<String> it = st.iterator();
		/*	for (String w : st)
		{
			System.out.printf("stack out %s\n", w);
		} */
		while(it.hasNext()){			
			String temp = it.next();
			System.out.printf("stack out %s\n", temp);
			}	
	}

		
			
}
