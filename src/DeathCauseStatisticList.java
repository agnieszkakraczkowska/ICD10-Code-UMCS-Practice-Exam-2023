import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DeathCauseStatisticList {
    List<DeathCauseStatistic> deathCauseStatistics = new ArrayList<>();

    public void repopulate(Path filePath) {
        deathCauseStatistics.clear();

        try {
            Scanner fileScanner = new Scanner(filePath);
            fileScanner.nextLine();
            fileScanner.nextLine();
            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                deathCauseStatistics.add(DeathCauseStatistic.fromCsvLine(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<DeathCauseStatistic> mostDeadlyDiseases(int age, int n) {
        Collections.sort(deathCauseStatistics, (d1,d2) -> {
            int d1Deaths = d1.getAgeBracketDeaths(age).deathCount;
            int d2Deaths = d2.getAgeBracketDeaths(age).deathCount;
            return Integer.compare(d2Deaths, d1Deaths);
        });

        return deathCauseStatistics.subList(0, n);
    }
}
