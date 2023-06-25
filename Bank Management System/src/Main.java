import java.util.*;

class Bank {
    private String name;
    private int netAmount;
    private Set<String> paymentModes;

    public Bank(String name, int netAmount, Set<String> paymentModes) {
        this.name = name;
        this.netAmount = netAmount;
        this.paymentModes = paymentModes;
    }

    public String getName() {
        return name;
    }

    public int getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(int netAmount) {
        this.netAmount = netAmount;
    }

    public Set<String> getPaymentModes() {
        return paymentModes;
    }
}

class Transaction {
    private Bank senderBank;
    private Bank receiverBank;
    private int amount;
    private String paymentMode;

    public Transaction(Bank senderBank, Bank receiverBank, int amount, String paymentMode) {
        this.senderBank = senderBank;
        this.receiverBank = receiverBank;
        this.amount = amount;
        this.paymentMode = paymentMode;
    }

    public Bank getSenderBank() {
        return senderBank;
    }

    public Bank getReceiverBank() {
        return receiverBank;
    }

    public int getAmount() {
        return amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        List<Bank> banks = new ArrayList<>();

        
        List<Transaction> transactions = new ArrayList<>();

        while (true) {
            System.out.println("\n----- Bank Management System Menu -----");
            System.out.println("1. Show all banks");
            System.out.println("2. Add a new bank");
            System.out.println("3. Show last transaction");
            System.out.println("4. Show bank details (total balance)");
            System.out.println("5. Transfer money");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    showAllBanks(banks);
                    break;
                case 2:
                    addNewBank(scanner, banks);
                    break;
                case 3:
                    showLastTransaction(transactions);
                    break;
                case 4:
                    showBankDetails(scanner, banks);
                    break;
                case 5:
                    transferMoney(scanner, banks, transactions);
                    break;
                case 0:
                    System.out.println("Exiting Bank Management System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
                    break;
            }
        }
    }

    private static void showAllBanks(List<Bank> banks) {
        
        int n = banks.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Bank bank1 = banks.get(j);
                Bank bank2 = banks.get(j + 1);
                if (bank1.getNetAmount() < bank2.getNetAmount()) {
                    
                    banks.set(j, bank2);
                    banks.set(j + 1, bank1);
                }
            }
        }

        System.out.println("\nAll Banks (Sorted by Net Amount):");
        for (int i = 0; i < banks.size(); i++) {
            Bank bank = banks.get(i);
            System.out.println("Bank " + (i + 1) + ": " + bank.getName() + " (Net Amount: " + bank.getNetAmount() + ")");
        }
    }

    private static void addNewBank(Scanner scanner, List<Bank> banks) {
        System.out.println("\nAdding a new bank:");
        System.out.print("Enter bank name: ");
        String name = scanner.nextLine();
        System.out.print("Enter net amount (balance): ");
        int netAmount = scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter payment modes (comma-separated): ");
        String paymentModesInput = scanner.nextLine();
        Set<String> paymentModes = new HashSet<>(Arrays.asList(paymentModesInput.split(",")));

        Bank newBank = new Bank(name, netAmount, paymentModes);
        banks.add(newBank);
        System.out.println("Bank added successfully!");
    }

    private static void showLastTransaction(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("\nNo transactions available.");
            return;
        }

        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        System.out.println("\nLast Transaction:");
        System.out.println("Sender Bank: " + lastTransaction.getSenderBank().getName());
        System.out.println("Receiver Bank: " + lastTransaction.getReceiverBank().getName());
        System.out.println("Amount: " + lastTransaction.getAmount());
        System.out.println("Payment Mode: " + lastTransaction.getPaymentMode());
    }

    private static void showBankDetails(Scanner scanner, List<Bank> banks) {
        System.out.println("\nBank Details (Total Balance):");
        showAllBanks(banks);

        System.out.print("Enter the bank index to view details: ");
        int bankIndex = scanner.nextInt();
        scanner.nextLine(); 

        if (bankIndex < 1 || bankIndex > banks.size()) {
            System.out.println("Invalid bank index!");
            return;
        }

        Bank bank = banks.get(bankIndex - 1);
        System.out.println("\nBank Name: " + bank.getName());
        System.out.println("Net Amount (Balance): " + bank.getNetAmount());
    }

    private static void transferMoney(Scanner scanner, List<Bank> banks, List<Transaction> transactions) {
        System.out.println("\nTransfer Money:");
        showAllBanks(banks);

        System.out.print("Enter sender bank index: ");
        int senderIndex = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter receiver bank index: ");
        int receiverIndex = scanner.nextInt();
        scanner.nextLine(); 

        if (senderIndex < 1 || senderIndex > banks.size() || receiverIndex < 1 || receiverIndex > banks.size()) {
            System.out.println("Invalid bank index!");
            return;
        }

        Bank senderBank = banks.get(senderIndex - 1);
        Bank receiverBank = banks.get(receiverIndex - 1);

        System.out.print("Enter the transfer amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter the payment mode: ");
        String paymentMode = scanner.nextLine();

        if (!senderBank.getPaymentModes().contains(paymentMode)) {
            System.out.println("Invalid payment mode for the sender bank!");
            return;
        }

        if (!receiverBank.getPaymentModes().contains(paymentMode)) {
            System.out.println("Invalid payment mode for the receiver bank!");
            return;
        }

        if (senderBank.getNetAmount() < amount) {
            System.out.println("Insufficient balance in the sender bank!");
            return;
        }

        senderBank.setNetAmount(senderBank.getNetAmount() - amount);
        receiverBank.setNetAmount(receiverBank.getNetAmount() + amount);

        Transaction transaction = new Transaction(senderBank, receiverBank, amount, paymentMode);
        transactions.add(transaction);

        System.out.println("Money transferred successfully!");
    }
}
