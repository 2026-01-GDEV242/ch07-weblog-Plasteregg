/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @author Andrew Wright
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    private int[] hourCounts;
    private LogfileReader reader;

    public LogAnalyzer()
    { 
        hourCounts = new int[24];
        reader = new LogfileReader();
    }

    public LogAnalyzer(String filename)
    {
        hourCounts = new int[24];
        reader = new LogfileReader(filename);
    }

    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }

    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
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
    public int busiestTwoHour()
    {
    int busiestStartHour = 0;
    int maxSum = hourCounts[0] + hourCounts[1]; // start with 0-1am

    for(int hour = 1; hour < 24; hour++)
    {
        int nextHour = (hour + 1) % 24;           // wraps around midnight
        int sum = hourCounts[hour] + hourCounts[nextHour];

        if(sum > maxSum)
        {
            maxSum = sum;
            busiestStartHour = hour;
        }
    }

    return busiestStartHour;
    }
    public int numberOfAccesses()
    {
    int total = 0;

    for(int hour = 0; hour < hourCounts.length; hour++)
    {
        total += hourCounts[hour];
    }

    return total;
    }
    
    public void printData()
    {
        reader.printData();
    }
}