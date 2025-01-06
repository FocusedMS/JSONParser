# JSON Parser

This is a simple JSON parser implemented in Java. It parses JSON strings into Java objects (Maps and Lists) by tokenizing the input JSON and then constructing a Java representation.

## Project Structure

- **Main.java**: Entry point for the JSON parsing application.
- **Tokenizer.java**: Responsible for tokenizing the input JSON string.
- **Parser.java**: Parses the tokenized input into Java objects.
- **ErrorHandler.java**: Handles error messages and exits the application.
- **JSONBuilder.java**: Converts Java objects back into JSON strings.
- **valid.json**: A sample valid JSON file.
- **invalid.json**: A sample invalid JSON file.

## Usage

1. Ensure the valid JSON file (`valid.json`) exists in the `resources/` directory.
2. Run `Main.java` to parse the JSON file and print the tokens and the parsed JSON object.

## To Run:

- Compile all the `.java` files.
- Run `Main.java`.

