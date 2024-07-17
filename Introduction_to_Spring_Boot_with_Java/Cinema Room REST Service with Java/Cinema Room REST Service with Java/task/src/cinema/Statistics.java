package cinema;

public class Statistics {
    private int income = 0;
    private int available = 81;
    private int purchased = 0;

    public Statistics() {
    }

    public Statistics(int income, int available, int purchased) {
        this.income = income;
        this.available = available;
        this.purchased = purchased;
    }

    public int getIncome() {
        return income;
    }

    public int getAvailable() {
        return available;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }

    public void addToIncome(int profit) {
        income += profit;
    }
    public void substractAvSeat(int seat) {
        available -= seat;
    }
    public void addToBoughtTicket(int ticket) {
        purchased += ticket;
    }
}