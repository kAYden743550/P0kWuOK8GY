// 代码生成时间: 2025-09-19 23:09:53
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Map;

public class MemoryUsageAnalysis {

    private MemoryMXBean memoryMXBean;

    /**
     * Initializes the MemoryMXBean for memory usage analysis.
     */
    public MemoryUsageAnalysis() {
        memoryMXBean = ManagementFactory.getMemoryMXBean();
    }

    /**
     * Retrieves the current memory usage details.
     * 
     * @return A string describing the memory usage.
     */
    public String getMemoryUsageDetails() {
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();

        StringBuilder memoryDetails = new StringBuilder();
        memoryDetails.append("Heap Memory Usage: 
")
                    .append("  Used: ").append(heapMemoryUsage.getUsed()).append(" bytes").append("
")
                    .append("  Committed: ").append(heapMemoryUsage.getCommitted()).append(" bytes").append("
")
                    .append("  Max: ").append(heapMemoryUsage.getMax()).append(" bytes").append("
")
                    .append("  Used Percent: ").append(heapMemoryUsage.getUsed() * 100 / heapMemoryUsage.getMax()).append("%").append("
");

        memoryDetails.append("Non-Heap Memory Usage: 
")
                    .append("  Used: ").append(nonHeapMemoryUsage.getUsed()).append(" bytes").append("
")
                    .append("  Committed: ").append(nonHeapMemoryUsage.getCommitted()).append(" bytes").append("
")
                    .append("  Max: ").append(nonHeapMemoryUsage.getMax()).append(" bytes").append("
")
                    .append("  Used Percent: ").append(nonHeapMemoryUsage.getUsed() * 100 / nonHeapMemoryUsage.getMax()).append("%");

        return memoryDetails.toString();
    }

    /**
     * Main method to run the memory usage analysis.
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {
            MemoryUsageAnalysis analysis = new MemoryUsageAnalysis();
            String usageDetails = analysis.getMemoryUsageDetails();
            System.out.println(usageDetails);
        } catch (Exception e) {
            System.err.println("An error occurred while analyzing memory usage: " + e.getMessage());
        }
    }
}