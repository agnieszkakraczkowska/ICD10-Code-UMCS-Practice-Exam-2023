import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class ICDCodeTabularOptimizedForMemory implements ICDCodeTabular {
    Path filePath;

    public ICDCodeTabularOptimizedForMemory(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getDescription(String ICDCode) throws IndexOutOfBoundsException {
        String result = null;
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
                    if(code.equals(ICDCode)) {
                        result = description;
                        break;
                    }
                }
                else
                    continue;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(result != null)
            return result;
        else throw new IndexOutOfBoundsException("ICDCode not found");
    }

    private boolean isValidICDCode(String code) {
        return code.matches("[A-Z]\\d{2}\\.\\d{1,2}");
    }
}
