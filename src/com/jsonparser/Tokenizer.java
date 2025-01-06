package com.jsonparser;

import java.util.*;

public class Tokenizer {

    public enum TokenType {
        LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
        COLON, COMMA, STRING, NUMBER, TRUE, FALSE, NULL, EOF
    }

    public static class Token {
        private TokenType type;
        private String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }

        public TokenType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        int i = 0;

        while (i < input.length()) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            switch (c) {
                case '{':
                    tokens.add(new Token(TokenType.LEFT_BRACE, "{"));
                    i++;
                    break;
                case '}':
                    tokens.add(new Token(TokenType.RIGHT_BRACE, "}"));
                    i++;
                    break;
                case '[':
                    tokens.add(new Token(TokenType.LEFT_BRACKET, "["));
                    i++;
                    break;
                case ']':
                    tokens.add(new Token(TokenType.RIGHT_BRACKET, "]"));
                    i++;
                    break;
                case ':':
                    tokens.add(new Token(TokenType.COLON, ":"));
                    i++;
                    break;
                case ',':
                    tokens.add(new Token(TokenType.COMMA, ","));
                    i++;
                    break;
                case '"':
                    int start = ++i;
                    while (i < input.length() && input.charAt(i) != '"') {
                        if (input.charAt(i) == '\\') i++; // Skip escaped characters
                        i++;
                    }
                    if (i >= input.length() || input.charAt(i) != '"') {
                        throw new IllegalArgumentException("Unclosed string literal");
                    }
                    String str = input.substring(start, i);
                    tokens.add(new Token(TokenType.STRING, str));
                    i++;
                    break;
                default:
                    if (Character.isDigit(c) || c == '-') {
                        int startNum = i;
                        while (i < input.length() && (Character.isDigit(input.charAt(i)) || input.charAt(i) == '.')) {
                            i++;
                        }
                        String num = input.substring(startNum, i);
                        tokens.add(new Token(TokenType.NUMBER, num));
                    } else if (input.startsWith("true", i)) {
                        tokens.add(new Token(TokenType.TRUE, "true"));
                        i += 4;
                    } else if (input.startsWith("false", i)) {
                        tokens.add(new Token(TokenType.FALSE, "false"));
                        i += 5;
                    } else if (input.startsWith("null", i)) {
                        tokens.add(new Token(TokenType.NULL, "null"));
                        i += 4;
                    } else {
                        throw new IllegalArgumentException("Unexpected character: " + c);
                    }
                    break;
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }
}
