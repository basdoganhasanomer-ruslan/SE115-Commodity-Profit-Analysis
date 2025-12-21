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
        try {
            for (int i = 0; i < MONTHS; i++) {
                String filename = "Data_Files/" + months[i] + ".txt";
                Scanner scanner = new Scanner(java.nio.file.Paths.get(filename));

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.trim().isEmpty())
                        continue;
                    if (line.startsWith("Day"))
                        continue;

                    String[] parts = line.split(",");
                    int day = Integer.parseInt(parts[0].trim());
                    String commodityName = parts[1].trim();
                    int profit = Integer.parseInt(parts[2].trim());

                    int commIndex = -1;
                    for (int c = 0; c < COMMS; c++) {
                        if (commodities[c].equals(commodityName)) {
                            commIndex = c;
                            break;
                        }
                    }

                    if (commIndex != -1) {
                        profits[i][day - 1][commIndex] = profit;
                    }
                }
                scanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
      int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }

        if (commIndex == -1) {
            return -1;
        }

        int maxStreak = 0;
        int currentStreak = 0;


        for (int m = 0; m < MONTHS; m++) {
         for (int d = 0; d < DAYS; d++) {
          if (profits[m][d][commIndex] < 0) {
                    currentStreak++;

                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                } else {

                    currentStreak = 0;
                }
            }
        }
        return maxStreak;   
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
    int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }

        if (commIndex == -1) {
            return -1;
        }

        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][commIndex] > threshold) {
                    count++;
                }
            }
        }
        return count; 
    }

    public static int biggestDailySwing(int month) { 
     if (month < 0 || month >= MONTHS) {
            return -99999;
        }

        int[] dailyTotals = new int[DAYS];
        for (int d = 0; d < DAYS; d++) {
            int total = 0;
            for (int c = 0; c < COMMS; c++) {
                total += profits[month][d][c];
            }
            dailyTotals[d] = total;
        }

        int maxSwing = 0;

        for (int i = 0; i < DAYS - 1; i++) {
            int swing = dailyTotals[i] - dailyTotals[i+1];

            if (swing < 0) {
                swing = -swing;
            }

            if (swing > maxSwing) {
                maxSwing = swing;
            }
        }
        return maxSwing; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
     int idx1 = -1, idx2 = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) idx1 = i;
            if (commodities[i].equals(c2)) idx2 = i;
        }

        if (idx1 == -1 || idx2 == -1) {
            return "INVALID_COMMODITY";
        }

        long total1 = 0, total2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                total1 += profits[m][d][idx1];
                total2 += profits[m][d][idx2];
            }
        }

        if (total1 > total2) {
            return c1 + " is better by " + (total1 - total2);
        } else if (total2 > total1) {
            return c2 + " is better by " + (total2 - total1);
        } else {
            return "Equal";
        } 
    }
    
    public static String bestWeekOfMonth(int month) { 
         if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }

        int maxWeekProfit = -2147483648;
        int bestWeekNum = 1;

        for (int w = 0; w < 4; w++) {
            int weekTotal = 0;

            for (int d = w * 7; d < (w + 1) * 7; d++) {
                for (int c = 0; c < COMMS; c++) {
                    weekTotal += profits[month][d][c];
                }
            }

            if (weekTotal > maxWeekProfit) {
                maxWeekProfit = weekTotal;
                bestWeekNum = w + 1;
            }
        }
        return "Week " + bestWeekNum;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
