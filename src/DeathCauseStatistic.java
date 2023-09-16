import java.util.ArrayList;
import java.util.List;

public class DeathCauseStatistic {
    private String ICD10Code;
    private List<AgeBracketDeaths> ageBracketDeaths = new ArrayList<>();

    public DeathCauseStatistic(String ICD10Code, List<AgeBracketDeaths> ageBracketDeaths) {
        this.ICD10Code = ICD10Code;
        this.ageBracketDeaths = ageBracketDeaths;
    }

    public String getICD10Code() {
        return ICD10Code;
    }

    public static DeathCauseStatistic fromCsvLine(String CSVLine) {
        String[] parts = CSVLine.split(",");
        String ICD10Code = parts[0].trim();
        List<AgeBracketDeaths> ageBracketDeaths = new ArrayList<>();
        int age = 0;
        for (int i = 2; i < parts.length; i++) {
            int young = age;
            String value = parts[i].trim();
            int intValue = value.equals("-") ? 0 : Integer.parseInt(value);
            age += 4;
            int old = age;
            ageBracketDeaths.add(new AgeBracketDeaths(young, old, intValue));
            age += 1;
        }
        return new DeathCauseStatistic(ICD10Code, ageBracketDeaths);
    }

    public AgeBracketDeaths getAgeBracketDeaths(int age) {
        for (var abd : ageBracketDeaths) {
            if (abd.young <= age && age < abd.old) {
                return abd;
            }
        }
        return null;
    }

    public static class AgeBracketDeaths {
        public final int young, old, deathCount;

        public AgeBracketDeaths(int young, int old, int deathCount) {
            this.young = young;
            this.old = old;
            this.deathCount = deathCount;
        }
    }
}
