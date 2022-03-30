public class Payment
{
	private Money amount;
	
	public Payment( Money cashTendered )
	{
		amount = cashTendered;
	}
	
	public Money getAmount() { 
		return amount; 
	}
}