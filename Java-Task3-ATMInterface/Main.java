public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();
       
         bank.addAccount(new Account("1001","Priscilla Etornam","1234", 5000.00));
         bank.addAccount(new Account("1002","Kojo Ganyo","1114", 2500.00));
         bank.addAccount(new Account("1003","Ama Gbordjuer","9872", 1500.00));
         bank.addAccount(new Account("1004","Herbert Nyamedor","4567", 3441.00));
         bank.addAccount(new Account("1005","Adams Boateng","9872", 1800.00));
         bank.addAccount(new Account("1006","Adloa Kat","9562", 1500.00));




         
         ATM atm = new ATM(bank);
         atm.start();

    }
}