package finalproject.DataStructures;
public class Triplet<T, U, V>
{
   private final T a;
   private final U b;
   private final V c;

   public Triplet(T a, U b, V c)
   {
	   this.a = a;
	   this.b = b;
	   this.c = c;
   }

   T getA(){ return a;}
   U getB(){ return b;}
   V getC(){ return c;}
}