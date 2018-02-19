
package business;

/**
 *
 * @author ptd
 */
public class Annuity extends Financial {
    public static final String TITLE = "Annuity";
    public static final String AMOUNTDESC = "Monthly Deposit:";
    public static final String RESULTDESC = "Final Value:";
    public static final String BEGBALDESC = "Beg.Bal";
    public static final String PRINFACTORDESC = "Deposite";
    public static final String INTRESTDESC = "Int. Earned";
    public static final String ENDBALDESC = "End.Bal";
    public static final String TITLEDESC = "Annuity";
    private boolean built;  //whether fv has been built
    //private double fv;     //final value of annuity
    private double[] bbal, iearn, ebal;
    
    public Annuity(){
        super();
    }
    public Annuity(double d, double r, int term) {
        super(d,r,term);
        this.built = false;
        if(super.isValid()){
            calcAnnuity();
        }
    }
    @Override
    public double getResult() {
        if (!built) {
            calcAnnuity();
        }
        return this.ebal[super.getTerm()-1];
    }
    @Override
    public double getBegBal(int mo) {
        if (!built){ 
            calcAnnuity(); 
        }
        if (mo < 1 || mo > super.getTerm()){ 
            return 0; 
        }
        return this.bbal[mo-1];
    }
    @Override
    public double getIntFactor(int mo) {
        if (!built) { calcAnnuity(); }
        if (mo < 1 || mo > this.getTerm()) { return 0; }
        return this.iearn[mo-1];
    }
    @Override
    public double getEndBal(int mo) {
        if (!built) { calcAnnuity(); }
        if (mo < 1 || mo > super.getTerm()) { return 0; }
        return this.ebal[mo-1];
    }
    private void calcAnnuity() {
        try{
            this.bbal = new double[super.getTerm()];
            this.iearn = new double[super.getTerm()];
            this.ebal = new double[super.getTerm()];

            this.bbal[0] = 0;
            for (int i=0; i<super.getTerm(); i++) {
                if (i > 0) {
                    this.bbal[i] = this.ebal[i-1];
 
                }
                this.iearn[i] = (this.bbal[i] + super.getAmt() )* (super.getRate()/12.0);
                this.ebal[i] = this.bbal[i] + this.iearn[i] + super.getAmt();
                //this.fv = this.fv + intearned + this.deposit;
            }
            this.built = true;
        }catch(NumberFormatException e){
            
        }    
    }

    @Override
    public double getPrinFactor(int mo) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    return super.getAmt();
    } 

    @Override
    public String setTitle() {
        return Annuity.TITLE;
    }
}
