package com.jsonparser;

import java.util.*;

public class Parser {

    private List<Tokenizer.Token> tokens;
    private int currentIndex;

    public Parser(List<Tokenizer.Token> tokens) {
        this.tokens = tokens;
        this.currentIndex = 0;
    }

    public Object parse() {
        Tokenizer.Token currentToken = currentToken();
        if (currentToken.getType() == Tokenizer.TokenType.LEFT_BRACE) {
            return parseObject();
        } else if (currentToken.getType() == Tokenizer.TokenType.LEFT_BRACKET) {
            return parseArray();
        } else {
            throw new IllegalArgumentException("Invalid JSON: Must start with { or [");
        }
    }

    private Map<String, Object> parseObject() {
        Map<String, Object> jsonObject = new HashMap<>();
        expect(Tokenizer.TokenType.LEFT_BRACE);

        while (currentToken().getType() != Tokenizer.TokenType.RIGHT_BRACE) {
            Tokenizer.Token keyToken = consume();
            if (keyToken.getType() != Tokenizer.TokenType.STRING) {
                throw new IllegalArgumentException("Expected a STRING key");
            }

            String key = keyToken.getValue();
            expect(Tokenizer.TokenType.COLON);

            Object value = parseValue();
            jsonObject.put(key, value);

            if (currentToken().getType() == Tokenizer.TokenType.COMMA) {
                consume();
            } else if (currentToken().getType() != Tokenizer.TokenType.RIGHT_BRACE) {
                throw new IllegalArgumentException("Expected ',' or '}'");
            }
        }

        expect(Tokenizer.TokenType.RIGHT_BRACE);
        return jsonObject;
    }

    private List<Object> parseArray() {
        List<Object> jsonArray = new ArrayList<>();
        expect(Tokenizer.TokenType.LEFT_BRACKET);

        while (currentToken().getType() != Tokenizer.TokenType.RIGHT_BRACKET) {
            Object value = parseValue();
            jsonArray.add(value);

            if (currentToken().getType() == Tokenizer.TokenType.COMMA) {
                consume();
            } else if (currentToken().getType() != Tokenizer.TokenType.RIGHT_BRACKET) {
                throw new IllegalArgumentException("Expected ',' or ']'");
            }
        }

        expect(Tokenizer.TokenType.RIGHT_BRACKET);
        return jsonArray;
    }

    private Object parseValue() {
        Tokenizer.Token token = currentToken();

        switch (token.getType()) {
            case STRING:
                consume();
                return token.getValue();
            case NUMBER:
                consume();
                return Double.parseDouble(token.getValue());
            case TRUE:
                consume();
                return true;
            case FALSE:
                consume();
                return false;
            case NULL:
                consume();
                return null;
            case LEFT_BRACE:
                return parseObject();
            case LEFT_BRACKET:
                return parseArray();
            default:
                throw new IllegalArgumentException("Unexpected token: " + token.getType());
        }
    }

    private Tokenizer.Token consume() {
        Tokenizer.Token token = currentToken();
        currentIndex++;
        return token;
    }

    private void expect(Tokenizer.TokenType expectedType) {
        if (currentToken().getType() != expectedType) {
            throw new IllegalArgumentException(
                    "Expected " + expectedType + " but found " + currentToken().getType()
            );
        }
        consume();
    }

    private Tokenizer.Token currentToken() {
        if (currentIndex >= tokens.size()) {
            throw new IllegalArgumentException("Unexpected end of input");
        }
        return tokens.get(currentIndex);
    }
}
