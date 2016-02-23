package finalproject.DataStructures;
public class Quad<S, T, U, V>
{
	private final S a;
	private final T b;
	private final U c;
	private final V d;

	public Quad(S a, T b, U c, V d)
	{
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	S getA(){ return a;}
	T getB(){ return b;}
	U getC(){ return c;}
	V getD(){ return d;}
}