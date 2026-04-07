/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @author Andrew Wright
 * @version    2016.02.29
 */
/**
 * The LogAnalyzer class analyzes web server log data by hour.
 * It provides methods to count accesses per hour, find the busiest
 * and quietest hours, and calculate total accesses.
 */
public class LogAnalyzer
{
    private int[] hourCounts; // Array storing the number of accesses for each hour (0-23)
    private LogfileReader reader; // Object used to read log file entries

    /**
     * Default constructor.
     * Initializes hourCounts array and a LogfileReader with no specific file.
     */
    public LogAnalyzer()
    { 
        hourCounts = new int[24];  // 24 hours in a day
        reader = new LogfileReader(); // Default log file reader
    }

    /**
     * Constructor with filename.
     * Initializes hourCounts array and a LogfileReader for the specified file.
     * 
     * @param filename The name of the log file to read.
     */
    public LogAnalyzer(String filename)
    {
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
    }

    /**
     * Analyze the log file and count the number of accesses for each hour.
     * Updates the hourCounts array based on the hour of each log entry.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();   // Get the next log entry
            int hour = entry.getHour();       // Extract the hour from the entry
            hourCounts[hour]++;               // Increment the count for that hour
        }
    }

    /**
     * Print the number of accesses for each hour.
     * Outputs in the format "Hr: Count" for hours 0 to 23.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Find the hour with the highest number of accesses.
     *
     * @return The hour (0-23) with the most accesses.
     */
    public int busiestHour()
    {
        int busiestHour = 0;
        int maxCount = hourCounts[0];

        for(int hour = 1; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] > maxCount)
            {
                maxCount = hourCounts[hour];
                busiestHour = hour;
            }
        }

        return busiestHour;
    }

    /**
     * Find the hour with the lowest number of accesses.
     *
     * @return The hour (0-23) with the fewest accesses.
     */
    public int quietestHour()
    {
        int quietestHour = 0;
        int minCount = hourCounts[0];

        for(int hour = 1; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] < minCount)
            {
                minCount = hourCounts[hour];
                quietestHour = hour;
            }
        }

        return quietestHour;
    }

    /**
     * Find the starting hour of the two-hour period with the most accesses.
     * Wraps around midnight if necessary.
     *
     * @return The starting hour (0-23) of the busiest two-hour period.
     */
    public int busiestTwoHour()
    {
        int busiestStartHour = 0;
        int maxSum = hourCounts[0] + hourCounts[1]; // sum for 0-1am

        for(int hour = 1; hour < 24; hour++)
        {
            int nextHour = (hour + 1) % 24;           // wrap around midnight
            int sum = hourCounts[hour] + hourCounts[nextHour];

            if(sum > maxSum)
            {
                maxSum = sum;
                busiestStartHour = hour;
            }
        }

        return busiestStartHour;
    }

    /**
     * Calculate the total number of accesses across all hours.
     *
     * @return Total number of accesses.
     */
    public int numberOfAccesses()
    {
        int total = 0;

        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            total += hourCounts[hour];
        }

        return total;
    }

    /**
     * Print the raw log data using the LogfileReader.
     */
    public void printData()
    {
        reader.printData();
    }
}