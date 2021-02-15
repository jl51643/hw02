package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class SmartScriptParserTest {

    @Test
    public void testPrimjer1() {
        String doc = readExample(1);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);

    }

    @Test
    public void testPrimjer2() {
        String doc = readExample(2);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    public void testPrimjer3() {
        String doc = readExample(3);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    @Disabled
    public void testPrimjer4() {
        String doc = readExample(4);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    @Disabled
    public void testPrimjer5() {
        String doc = readExample(5);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    public void testPrimjer6() {
        String doc = readExample(6);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    public void testPrimjer7() {
        String doc = readExample(7);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }
    @Test
    @Disabled
    public void testPrimjer8() {
        String doc = readExample(8);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    @Disabled
    public void testPrimjer9() {
        String doc = readExample(9);
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    private String readExample(int n) {
        try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
            if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
            byte[] data = is.readAllBytes();
            String text = new String(data, StandardCharsets.UTF_8);
            return text;
        } catch(IOException ex) {
            throw new RuntimeException("Greška pri čitanju datoteke.", ex);
        }
    }

    private String loader(String filename) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try(InputStream is =
                    this.getClass().getClassLoader().getResourceAsStream(filename)) {
            byte[] buffer = new byte[1024];
            while(true) {
                int read = is.read(buffer);
                if(read<1) break;
                bos.write(buffer, 0, read);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        } catch(IOException ex) {
            return null;
        }
    }

    @Test
    public void testForLoop() {
        String doc = "Some string at the begening" +
                "{$FOR variableName start end step$}" +
                "child1" +
                "{$= \"child2\" $}" +
                "{$END$}" +
                "text Node at the end";
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    public void testExample() {
        String doc = "This is sample text.\n" +
                "{$ FOR i 1 10 1 $}\n" +
                " This is {$= i $}-th time this message is generated.\n" +
                "{$END$}\n" +
                "{$FOR i 0 10 2 $}\n" +
                " sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n" +
                "{$END$}\n";
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

    @Test
    public void testForLoopTreeArguments() {
        String doc = "This is sample text.\n" +
                "{$ FOR i 1 10 $}\n" +
                "some text node" +
                "{$END$}\n";
        SmartScriptParser parser = new SmartScriptParser(doc);
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = document.toString();
        SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
        DocumentNode document2 = parser2.getDocumentNode();
// now document and document2 should be structurally identical trees
        boolean same = document.equals(document2); // ==> "same" must be true
        System.out.println(document.toString());
        System.out.println();
        System.out.println(document2.toString());
        assertTrue(same);
    }

}