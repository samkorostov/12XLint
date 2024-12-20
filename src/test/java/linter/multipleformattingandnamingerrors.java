package linter;

// Note: don't hate me all the issues here are deliberate
public class multipleformattingandnamingerrors { // Class name should be in PascalCase

    public static final int MAX_VALUE = 100; // Correct constant name
    public static final int Min_Value = 50; // incorrect constant name

    private int fieldValue; // Correct field name
    private int FieldValue; // Field name should be in camelCase

    public void validMethod() { // Method name should be in camelCase
        int validVariable = 5; int anotheOne = 4;
        System.out.println("Hello"); System.out.println("World"); // multiple statement error + long line because of this comment
        int thisISNOTAVALIDNAMEDVARIABLE = 9999999;
        if (validVariable > anotheOne) {
            anotheOne = validVariable;
        }

        for (int i = 0; i < 5; i++ ) {
            System.out.println("Misaligned"); // Indentation error
        }
    }

    public void should_be_Camel_cased() { // Method naming error
        int a = 5; int b = 10;
        for (int i = 0; i < 5; i++) {
            a++; b++;
        }
    }

    public static void Main(String[] args) { // Method name should be in camelCase
        multipleformattingandnamingerrors instance = new multipleformattingandnamingerrors();
        instance.validMethod();
        instance.should_be_Camel_cased();
    }
}
