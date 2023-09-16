import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ICDCodeTabularOptimizedForTime implements ICDCodeTabular {
    Map<String,String> codeDescription = new HashMap<>();
    private Path filePath;

    public ICDCodeTabularOptimizedForTime(Path filePath) {
        this.filePath = filePath;

        try {
            Scanner fileScanner = new Scanner(filePath);
            for(var i = 1; i < 88; i++)
                fileScanner.nextLine();

            while(fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                line = line.trim();
                String[] lineParts = line.split(" ",2);
                if(lineParts.length < 2)
                    continue;

                String code = lineParts[0].trim();
                String description = lineParts[1].trim();

                if(isValidICDCode(code)) {
                    codeDescription.put(code,description);
                }
                else
                    continue;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getDescription(String ICDCode) throws IndexOutOfBoundsException {
        String description = null;
        for(var code : codeDescription.keySet()) {
            if(code.equals(ICDCode)) {
                description = codeDescription.get(code);
                break;
            }
        }
        if(description != null)
            return description;
        else throw new IndexOutOfBoundsException("ICDCode not found");
    }

    private boolean isValidICDCode(String code) {
        return code.matches("[A-Z]\\d{2}(\\.\\d{1,2})?");
    }
}
