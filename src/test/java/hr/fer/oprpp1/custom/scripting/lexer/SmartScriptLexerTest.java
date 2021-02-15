package hr.fer.oprpp1.custom.scripting.lexer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SmartScriptLexerTest {

    @Test
    public void testNextToken() {
        SmartScriptLexer lexer = new SmartScriptLexer("text 123 {$=123$}");
        assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
        assertEquals(SmartScriptTokenType.TAG,lexer.nextToken().getType());
        lexer.setState(SmartScriptLexerState.TAG);
        assertEquals(SmartScriptTokenType.TAG_NAME,lexer.nextToken().getType());
        assertEquals(SmartScriptTokenType.INTEGER,lexer.nextToken().getType());
        assertEquals(SmartScriptTokenType.TAG,lexer.nextToken().getType());
        lexer.setState(SmartScriptLexerState.BASIC);
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());

        SmartScriptLexer lexer2 = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");
        assertEquals(SmartScriptTokenType.TEXT, lexer2.nextToken().getType());
        System.out.println(lexer2.getToken().getValue().toString());
        assertEquals(SmartScriptTokenType.TAG, lexer2.nextToken().getType());
        System.out.println(lexer2.getToken().getValue().toString());
        lexer2.setState(SmartScriptLexerState.TAG);
        assertEquals(SmartScriptTokenType.TAG_NAME, lexer2.nextToken().getType());
        System.out.println(lexer2.getToken().getValue().toString());
        assertEquals(SmartScriptTokenType.INTEGER, lexer2.nextToken().getType());
        System.out.println(lexer2.getToken().getValue().toString());
        assertEquals(SmartScriptTokenType.TAG, lexer2.nextToken().getType());
        System.out.println(lexer2.getToken().getValue().toString());
        assertEquals(SmartScriptTokenType.EOF, lexer2.nextToken().getType());

        SmartScriptLexer lexer3 = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
        assertEquals(SmartScriptTokenType.TEXT, lexer3.nextToken().getType());
        assertEquals(SmartScriptTokenType.TAG, lexer3.nextToken().getType());
        lexer3.setState(SmartScriptLexerState.TAG);
        assertEquals(SmartScriptTokenType.TAG_NAME, lexer3.nextToken().getType());
        assertEquals(SmartScriptTokenType.TEXT, lexer3.nextToken().getType());
        System.out.println(lexer3.getToken().getValue().toString());
        assertEquals(SmartScriptTokenType.TAG, lexer3.nextToken().getType());
        lexer3.setState(SmartScriptLexerState.BASIC);
        assertEquals(SmartScriptTokenType.TEXT, lexer3.nextToken().getType());
        assertEquals(SmartScriptTokenType.EOF, lexer3.nextToken().getType());
    }

    @Test
    public void testGetToken() {
        SmartScriptLexer lexer = new SmartScriptLexer("text 123 {$=123$}");
        lexer.nextToken();
        assertEquals(SmartScriptTokenType.TEXT, lexer.getToken().getType());
        assertEquals(SmartScriptTokenType.TEXT, lexer.getToken().getType());
        assertEquals(SmartScriptTokenType.TEXT, lexer.getToken().getType());
        lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG,lexer.getToken().getType());
        assertEquals(SmartScriptTokenType.TAG,lexer.getToken().getType());
    }

}