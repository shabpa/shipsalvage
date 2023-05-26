public enum FieldState {
    EMPTY(' '), MISS('X'), OCCUPIED_HIDDEN('O'), OCCUPIED_SALVAGED('#');

    private final char output;

    private FieldState(char output) {
        this.output = output;
    }

    public char getOutput() {
        return output;
    }

    public static FieldState fromOutput(char output) {

        for (FieldState fieldstate : FieldState.values())
            if (fieldstate.getOutput() == output)
                return fieldstate;

        throw new IllegalArgumentException("Für den Wert gibt es keinen Eintrag!");

    }

}