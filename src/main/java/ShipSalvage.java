import java.util.Scanner;

public class ShipSalvage {

    // exampleMap
    private static final char[][] exampleMap = {
            { 'O', 'O', ' ', 'O', ' ', ' ', 'O', 'O', 'O', 'O' },
            { ' ', ' ', ' ', 'O', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'O', ' ', ' ', 'O', ' ', 'O', 'O', 'O', ' ', 'O' },
            { 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'O' },
            { 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { 'O', ' ', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O' },
            { ' ', ' ', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O' },
            { 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'O' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', 'O', 'O', 'O', 'O', 'O', 'O' },
    };

    // Return two dimensional FieldState array as play map for ShipSalvage
    public static FieldState[][] getExample() {

        // FieldState array
        FieldState[][] fieldStateMap = new FieldState[exampleMap.length][exampleMap[0].length];

        // Converting char values of two dimensional char array to FieldState values
        // using the fromOutput method of enum FieldState
        for (int row = 0; row < exampleMap.length; row++)
            for (int column = 0; column < exampleMap[row].length; column++)
                fieldStateMap[row][column] = FieldState.fromOutput(exampleMap[row][column]);

        return fieldStateMap;

    }

    // Checking if map created by getExample method is a valid play map
    public static void checkValidMap(FieldState[][] map) {

        if (map == null)
            throw new IllegalArgumentException("Map can't be null!");

        if (map.length != 10)
            throw new IllegalArgumentException("Map must have ten rows!");

        for (int row = 0; row < map.length; row++) {
            if (map[row] == null)
                throw new IllegalArgumentException("The rows of the map can't be null!");

            if (map[row].length != 10)
                throw new IllegalArgumentException("Map must have ten columns!");

            for (int column = 0; column < map[row].length; column++)
                if (map[row][column] == null)
                    throw new IllegalArgumentException("Fields of map can't be null!");

        }

    }

    // Method for terminal output of map
    public static void printMap(FieldState[][] map, boolean showHidden) {

        String border = "   +----------+\n";

        // Column indicators and border
        System.out.printf("    ABCDEFGHIJ\n");
        System.out.printf(border);

        // Iterate over two dimensional FieldState array to print out the values of the
        // elements
        for (int row = 0; row < map.length; row++) {

            // Row indicators incremented by one to start from 1 and not 0
            System.out.printf("%2d |", row + 1);

            for (int column = 0; column < map[row].length; column++) {
                if (!showHidden && map[row][column] == FieldState.OCCUPIED_HIDDEN)
                    System.out.printf(" ");

                else
                    System.out.printf("%s", map[row][column].getOutput());

            }
            // Adding new line after every row
            System.out.printf("|\n");
        }

        // Bottom border
        System.out.printf(border);

    }

    // Checking if map still has OCCUPIED_HIDDEN fields
    public static boolean allSalvaged(FieldState[][] map) {

        for (int row = 0; row < map.length; row++)
            for (int column = 0; column < map[row].length; column++)
                if (map[row][column] == FieldState.OCCUPIED_HIDDEN)
                    return false;

        return true;
    }

    public static boolean isNumeric(String row) {
        if (row == null)
            return false;
        try {
            Integer i = Integer.parseInt(row);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void probeField(FieldState[][] map, String field) {

        field = field.toUpperCase();

        if (field.indexOf(" ") != -1) {
            System.out.println("Input can't have whitespace in in.");
            return;
        }

        if (field.isBlank()) {
            System.out.println("Input can't be empty!");
            return;
        }

        char column_input = field.charAt(0);
        int column = (int) column_input - 65;

        if (column > 10 || column < 0) {
            System.out.println("Column only A-J are allowed!");
            return;
        }

        String row_input = field.substring(1, field.length());
        int row;

        if (isNumeric(row_input)) {
            row = Integer.parseInt(row_input);
        } else {
            System.out.println("Row only accepts numeric input!");
            return;
        }

        if (row >= 11 || row <= 0) {
            System.out.println("Row only 1-10 are allowed!");
            return;
        }

        row = row - 1;

        switch (map[row][column]) {
            case EMPTY:
                map[row][column] = FieldState.MISS;
                System.out.printf("Nichts zu finden!\n");
                return;

            case OCCUPIED_HIDDEN:
                map[row][column] = FieldState.OCCUPIED_SALVAGED;
                System.out.printf("Wrack gefunden!\n");
                return;
            case OCCUPIED_SALVAGED:
                System.out.printf("Bereits untersucht!\n");
                return;

            case MISS:
                System.out.printf("Bereits untersucht!\n");
                return;

        }

    }

    public static void main(String[] args) {

        FieldState[][] map = getExample();
        Scanner scanner = new Scanner(System.in);

        while (!allSalvaged(map)) {
            printMap(map, false);
            String input = scanner.next();
            probeField(map, input);
        }

        System.out.printf("Alle Schiffe geborgen!");

    }

}
