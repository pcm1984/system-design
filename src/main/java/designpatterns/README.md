## Strategy Pattern

           +-------------------+
           | PaymentStrategy   |<-----------------+
           +-------------------+                  |
           | + pay(amount):void|                  |
           +-------------------+                  |
                    ^                             |
         +--------------------+   +---------------------+
         | CreditCardPayment  |   | PayPalPayment       |
         +--------------------+   +---------------------+
         | - cardNumber: String |   | - email: String    |
         | + pay(amount): void  |   | + pay(amount): void|
         +--------------------+   +---------------------+
                         ^
         +--------------------+
         | UpiPayment          |
         +--------------------+
         | - upiId: String     |
         | + pay(amount): void |
         +--------------------+

                        +--------------------+
                        |   PaymentContext   |
                        +--------------------+
                        | - paymentStrategy: |
                        |   PaymentStrategy  |
                        | + setPaymentStrategy|
                        | + pay(amount): void|
                        +--------------------+
