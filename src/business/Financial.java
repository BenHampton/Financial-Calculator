
package business;

/**
 *
 * @author Ben Hampton
 */
abstract public class Financial {
    private double amt, rate;
    private int term;
    private String emsg;
    
    public Financial(){
        this.amt = 0;
        this.rate = 0;
        this.term = 0;
    }
    public Financial(double a, double r, int t){
        this.amt = a;
        this.rate = r;
        this.term = t;
        if(isValid()){
        }
    }
    protected boolean isValid(){
        this.emsg = "";
        if(this.getAmt() <= 0){
            this.emsg = "Amount not a positive value";
        }
        if (this.getRate() <= 0){
            this.emsg = "Rate is not a positve value";
        }
        if(this.getTerm() <= 0){
            this.emsg = "Term is not a positve value";
        }
        return this.emsg.isEmpty();
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getErrorMessage() {
        return emsg;
    }
    abstract public double getResult();
    abstract public double getBegBal(int mo);
    abstract public double getPrinFactor(int mo);
    abstract public double getIntFactor(int mo);
    abstract public double getEndBal(int mo);
    abstract public String setTitle();
    
}
