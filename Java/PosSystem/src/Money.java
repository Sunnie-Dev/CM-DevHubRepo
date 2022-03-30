
import java.math.BigDecimal;
// Author : Casey Munga : 03/29/2022
// Reason: Project required money but there was no given class POS SYSTEM

public class Money {

	BigDecimal money;
	
	public Money() {
		
	}
	
	public  Money(double _money){
		
		this.money = BigDecimal.valueOf(_money);
		this.money.abs();
		
	}

	public Money times(int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(Money subtotal) {
		// TODO Auto-generated method stub
		
	}

	public Money minus(Money total) {
		// TODO Auto-generated method stub
		return null;
	}

}
