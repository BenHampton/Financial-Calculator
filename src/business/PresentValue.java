
package business;

/**
 *
 * @author Ben Hampton
 */
public class PresentValue extends Financial {
    public static final String TITLEDESC = "Present Value";
    public static final String AMOUNTDESC = "Future Amount";
    public static final String RESULTDESC = "Present Value";
    public static final String DISCDESC = "Discont";
    public static final String PVDESC = "Present Value";
    private double[] bbal, ebal, intrate, pvbal;
    double presentValue;
    private boolean built;
    
    public PresentValue(){
        super();
    }
    public PresentValue(double amt, double rate, int term){
        super(amt,rate,term);
        this.built = false;
        if(super.isValid()){
        buildPV();
    }
        
    }

    @Override
    public double getResult() {
        return this.ebal[super.getTerm()-1];
    }

    @Override
    public double getBegBal(int mo) {
         return this.bbal[mo-1];
    }

    @Override
    public double getPrinFactor(int mo) {
        //discount
        return this.intrate[mo];
    }

    @Override
    public double getIntFactor(int mo) {
        return 0;
    }

    @Override
    public double getEndBal(int mo) {
        // present value
        return this.ebal[mo];
    }

    @Override
    public String setTitle() {
        return PresentValue.TITLEDESC;
    }
    
    private void buildPV(){
        try{
            this.bbal = new double[super.getTerm()];
            this.intrate = new double[super.getTerm()];
            this.ebal = new double[super.getTerm()];
            this.pvbal = new double[super.getTerm()];
            
            this.bbal[0] = super.getAmt();
            
            double morate = (super.getRate() / 12.0);
            double denom = Math.pow((1+morate), super.getTerm());
            this.presentValue = super.getAmt() / denom;

            for(int i = 0; i < super.getTerm(); i++){
                if(i > 0){
                    this.bbal[i] = this.ebal[i-1];
                }

                this.intrate[i] = (this.presentValue * morate) + this.presentValue; // discount 
                this.ebal[i] = (this.bbal[i] - this.presentValue) - this.intrate[i];// present value 

                //WORKING
                //this.pvbal[i] = this.bbal[i] - (this.bbal[i]  / morate);
                //this.intrate[i] = super.getAmt() - this.bbal[i]; // discount 
                //this.ebal[i] = this.bbal[i] - this.pvbal[i] ;// present value 
                
                //2000 to 0
               //super.getAmt() - (super.getAmt() - this.bbal[i])

                
               }
            
            this.built = true;
        } catch(NumberFormatException e){
            
        }
    }
}

