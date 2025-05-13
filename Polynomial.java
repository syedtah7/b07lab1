public class Polynomial  {
	double [] coeffecients;
	
	public Polynomial(){
		coeffecients = new double[0];

	} 
	public Polynomial(double [] a){
		coeffecients = new double[a.length];
		for (int i = 0; i<a.length; i++){
			coeffecients[i] = a[i];
		}
	}
	public Polynomial add(Polynomial added){
		int max_length = Math.max(this.coeffecients.length, added.coeffecients.length);
		double [] addition = new double[max_length];
		for (int i = 0; i < max_length; i++) {
        		double a = 0;
       			double b = 0;
			if (i< this.coeffecients.length){
				a = this.coeffecients[i];
			}
			if (i< added.coeffecients.length){
				b = added.coeffecients[i];
			}
		addition[i] = a+b;
		}
		Polynomial result = new Polynomial(addition);
		return result;
	}
	public double evaluate(double x){
		double result = 0;
		for (int i=0; i<coeffecients.length; i++){
			result += coeffecients[i]*Math.pow(x,i);
		}
		return result;
	}
	public boolean hasRoot(double x){
		return(evaluate(x)==0);
	}
}