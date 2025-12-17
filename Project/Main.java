// Main.java — Students version
import java.io.*;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    static int[][][] profits = new int[MONTHS][DAYS][COMMS];

    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {

                try {
                    File file = new File("Data_Files/" + months[m] + ".txt");
                    Scanner sc = new Scanner(file);

                    while (sc.hasNextLine()) {

                        String line = sc.nextLine();
                        String[] parts = line.split(",");

                        int day = Integer.parseInt(parts[0]) - 1;
                        String commName = parts[1];
                        int profit = Integer.parseInt(parts[2]);

                        int commIndex = -1;
                        for (int i = 0; i < COMMS; i++) {
                            if (commodities[i].equals(commName)) {
                                commIndex = i;
                                break;
                            }
                        }

                        if (day >= 0 && day < DAYS && commIndex != -1) {
                            profits[m][day][commIndex] = profit;
                        }
                    }

                    sc.close();

                } catch (Exception e) {

                }
            }
        }


    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
    if (month < 0 || month >= MONTHS) {
        return "INVALID_MONTH";
    }
int maxProfit = -2147483648;
String bestComm = "";

for (int c = 0; c < COMMS; c++) {
        int currentCommTotal = 0;
        
        for (int d = 0; d < DAYS; d++) {
            currentCommTotal += profits[month][d][c];
        }
if (currentCommTotal > maxProfit) {
            maxProfit = currentCommTotal;
            bestComm = commodities[c];
        }
    }

    return bestComm + " " + maxProfit; 
    }

    public static int totalProfitOnDay(int month, int day) {
        int d = day - 1;
        int total = 0;

        for (int c = 0; c < COMMS; c++) {
            total += profits[month][d][c];
        }

        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1 || from < 1 || to > 28 || from > to) {
            return -99999;
        }
        int totalRangeProfit = 0;
        
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d <= to - 1; d++) {
                totalRangeProfit += profits[m][d][commIndex];
            }
        }
        return totalRangeProfit;
    }

    public static int bestDayOfMonth(int month) { 
         if (month < 0 || month >= MONTHS) {
            return -1;
        }
        
        int maxProfit = -2147483648;
        int bestDay = 1;

        for (int d = 0; d < DAYS; d++) {
            int dailyTotal = 0;
            for (int c = 0; c < COMMS; c++) {
                dailyTotal += profits[month][d][c];
            }
            if (dailyTotal > maxProfit) {
                maxProfit = dailyTotal;
                bestDay = d + 1;
            }
        }
        return bestDay;  
    }
    
    public static String bestMonthForCommodity(String comm) { 
        int commIndex = -1;
            for (int i = 0; i < COMMS; i++) {
                if (commodities[i].equals(comm)) {
                    commIndex = i;
                    break;
                }
            }

            if (commIndex == -1) {
                return "INVALID_COMMODITY";
            }

            int maxProfit = -2147483648;
            String bestMonthName = "";

            for (int m = 0; m < MONTHS; m++) {
                int monthlyTotal = 0;
                for (int d = 0; d < DAYS; d++) {
                    monthlyTotal += profits[m][d][commIndex];
                }
                if (monthlyTotal > maxProfit) {
                    maxProfit = monthlyTotal;
                    bestMonthName = months[m];
                }
            }
            return bestMonthName; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
