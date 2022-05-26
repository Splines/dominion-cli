package me.splines.dominion.card;

public final class CardFormatterUtil {

    private CardFormatterUtil() {
    }

    public static String wrapLine(String line, int lineLength, String linebreak) {
        // inspired by https://stackoverflow.com/a/45614206
        if (line.length() == 0)
            return "";
        if (line.length() <= lineLength)
            return line;

        String[] words = line.split(" ");
        StringBuilder allLines = new StringBuilder();
        StringBuilder trimmedLine = new StringBuilder();

        for (String word : words) {
            if (trimmedLine.length() + 1 + word.length() <= lineLength) {
                trimmedLine.append(word).append(" ");
            } else {
                // remove last whitespace
                trimmedLine.setLength(trimmedLine.length() - 1);
                allLines.append(trimmedLine).append(linebreak);
                trimmedLine = new StringBuilder();
                trimmedLine.append(word).append(" ");
            }
        }

        if (trimmedLine.length() > 0) {
            allLines.append(trimmedLine);
        }

        return allLines.toString();
    }

}
