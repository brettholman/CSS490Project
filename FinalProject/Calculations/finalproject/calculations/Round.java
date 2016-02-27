package finalproject.calculations;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {
	public static double RoundMoney(double value)
	{
		if(value <= 0)
		{
			return -1;
		}
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
}
