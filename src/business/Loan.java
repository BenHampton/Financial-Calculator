
package business;

/**
 *
 * @author ptd
 */
public class Loan extends Financial{
    public static final String TITLE = "Loan";
    public static final String AMOUNTDESC = "Laon Amount:";
    public static final String RESULTDESC = "Monthly Laon Payment:";
    public static final String BEGBALDESC = "Beg.Bal";
    public static final String PAYMENTDESC = "Payment";
    public static final String INTRESTDESC = "Int. Earned";
    public static final String ENDBALDESC = "End.Bal";
    public static final String PRINPAIDDESC = "Prin.Paid";
    public static final String TITLEDESC = "LOAN";

    private double mopmt;
    private boolean built;
    private double[] bbal, ichg, ebal;
    
    public Loan(){
        super();
    }
    public Loan(double p, double r, int t) {
        super(p,r,t);
        mopmt = 0;
        built = false;
        if(super.isValid()){
            buildLoan();
        }
    }

    @Override
    public double getResult() {
        if (!built) { buildLoan(); }
        return mopmt;
    }

    @Override
    public double getBegBal(int mo) {
        if (!built) { buildLoan(); }
        if (mo < 1 || mo > super.getTerm()) { return 0; }
        return this.bbal[mo-1];
    }
    @Override
    public double getIntFactor(int mo) {
        if (!built) { buildLoan(); }
        if (mo < 1 || mo > super.getTerm()) { return 0; }
        return this.ichg[mo-1];
    }
    @Override
    public double getEndBal(int mo) {
        if (!built) { buildLoan(); }
        if (mo < 1 || mo > super.getTerm()) { return 0; }
        return this.ebal[mo-1];
    }
    @Override
    public double getPrinFactor(int mo) {
        if(!built){
            buildLoan();
        }
        return this.mopmt;
    }
    public double getPrinPaid(int mo){
        if( !built ){
            buildLoan();
        }
        if( mo < 1 || mo > super.getTerm()){
            return 0;
        }
        return (this.ebal[mo -1] - this.bbal[mo -1]);
    }
    private void buildLoan() {
        //calculate Monthly Payment....
        double morate = super.getRate() / 12.0;
        double denom = Math.pow((1+morate),super.getTerm()) - 1;
        this.mopmt = (morate + morate/denom) * super.getAmt();
        
        this.bbal = new double[super.getTerm()];
        this.ichg = new double[super.getTerm()];
        this.ebal = new double[super.getTerm()];
        
        this.bbal[0] = super.getAmt();
        
        for(int i=0; i< super.getTerm(); i++) {
            if (i > 0) {
                this.bbal[i] = this.ebal[i-1];
            }
            this.ichg[i] = this.bbal[i] * morate;
            this.ebal[i] = this.bbal[i] + this.ichg[i] - this.mopmt;
         }
        this.built = true;
    }

    @Override
    public String setTitle() {
        return this.TITLEDESC;
    }


}
