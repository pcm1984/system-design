package designpatterns;

class PaypalPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount){
        System.out.print("Paying via Paypal." + amount);
    }
}

interface PaymentStrategy{
    public void pay(int amount);
}

class UpiPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount){
        System.out.print("Paying via UPI." + amount);
    }
}

class CreditCardPaymentStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount){
        System.out.print("Paying via Credit Card." + amount);
    }
}

class PaymentContext{
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    public void pay(int amount){
        this.paymentStrategy.pay(amount);
    }
}

public class StrategyPatternImpl{

    public static void main(String args[]){
        PaymentContext paymentContext = new PaymentContext();
        PaymentStrategy paymentStrategy = new CreditCardPaymentStrategy();
        paymentContext.setPaymentStrategy(paymentStrategy);
        paymentContext.pay(200);

    }
}