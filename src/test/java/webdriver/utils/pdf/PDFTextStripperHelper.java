package webdriver.utils.pdf;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.pdfbox.text.TextPositionComparator;
import org.apache.pdfbox.util.QuickSort;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.Bidi;
import java.text.Normalizer;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class PDFTextStripperHelper extends PDFTextStripper {
    private static final Log LOG = LogFactory.getLog(PDFTextStripperHelper.class);

    /**
     * Instantiate a new PDFTextStripper object.
     *
     * @throws IOException If there is an error loading the properties.
     */
    public PDFTextStripperHelper() throws IOException {
    }

    public List<List<TextPosition>> getCharactersByArticle() {
        return super.getCharactersByArticle();
    }

//    private static final float END_OF_LAST_TEXT_X_RESET_VALUE = -1;
//    private static final float MAX_Y_FOR_LINE_RESET_VALUE = -Float.MAX_VALUE;
//    private static final float EXPECTED_START_OF_NEXT_WORD_X_RESET_VALUE = -Float.MAX_VALUE;
//    private static final float MAX_HEIGHT_FOR_LINE_RESET_VALUE = -1;
//    private static final float MIN_Y_TOP_FOR_LINE_RESET_VALUE = Float.MAX_VALUE;
//    private static final float LAST_WORD_SPACING_RESET_VALUE = -1;
//    private static final boolean useCustomQuickSortHelper;
//
//    private static Map<Character, Character> MIRRORING_CHAR_MAP = new HashMap<>();
//
//    static {
//        boolean is16orLess = false;
//        try {
//            String version = System.getProperty("java.specification.version");
//            StringTokenizer st = new StringTokenizer(version, ".");
//            int majorVersion = Integer.parseInt(st.nextToken());
//            int minorVersion = 0;
//            if (st.hasMoreTokens()) {
//                minorVersion = Integer.parseInt(st.nextToken());
//            }
//            is16orLess = majorVersion == 1 && minorVersion <= 6;
//        }
//        catch (SecurityException | NumberFormatException x) {
//            LOG.error(x);
//        }
//        useCustomQuickSortHelper = !is16orLess;
//    }
//
//    static {
//        String path = "org/apache/pdfbox/resources/text/BidiMirroring.txt";
//        try (InputStream input = PDFTextStripper.class.getClassLoader().getResourceAsStream(path)) {
//            if (input != null) {
//                parseBidiFile(input);
//            }
//            else {
//                LOG.warn("Could not find '" + path + "', mirroring char map will be empty: ");
//            }
//        }
//        catch (IOException e) {
//            LOG.warn("Could not parse BidiMirroring.txt, mirroring char map will be empty: " + e.getMessage());
//        }
//    }
//
//    /**
//     * This method parses the bidi file provided as inputstream.
//     *
//     * @param inputStream - The bidi file as inputstream
//     * @throws IOException if any line could not be read by the LineNumberReader
//     */
//    private static void parseBidiFile(InputStream inputStream) throws IOException {
//        LineNumberReader rd = new LineNumberReader(new InputStreamReader(inputStream));
//        do {
//            String s = rd.readLine();
//            if (s == null) {
//                break;
//            }
//            int comment = s.indexOf('#'); // ignore comments
//            if (comment != -1) {
//                s = s.substring(0, comment);
//            }
//            if (s.length() < 2) {
//                continue;
//            }
//            StringTokenizer st = new StringTokenizer(s, ";");
//            int nFields = st.countTokens();
//            Character[] fields = new Character[nFields];
//            for (int i = 0; i < nFields; i++) {
//                fields[i] = (char) Integer.parseInt(st.nextToken().trim(), 16);
//            }
//            if (fields.length == 2) {
//                MIRRORING_CHAR_MAP.put(fields[0], fields[1]);
//            }
//        } while (true);
//    }
//
//    /**
//     * This will process the contents of a page.
//     *
//     * @param page The page to process.
//     *
//     * @throws IOException If there is an error processing the page.
//     */
//    public void processPage(PDPage page) throws IOException {
//        writePage();
//    }
//
//    /**
//     * This will print the text of the processed page to "output". It will estimate, based on the coordinates of the
//     * text, where newlines and word spacings should be placed. The text will be sorted only if that feature was
//     * enabled.
//     *
//     * @throws IOException If there is an error writing the text.
//     */
//    protected void writePage() throws IOException {
//        float maxYForLine = MAX_Y_FOR_LINE_RESET_VALUE;
//        float minYTopForLine = MIN_Y_TOP_FOR_LINE_RESET_VALUE;
//        float endOfLastTextX = END_OF_LAST_TEXT_X_RESET_VALUE;
//        float lastWordSpacing = LAST_WORD_SPACING_RESET_VALUE;
//        float maxHeightForLine = MAX_HEIGHT_FOR_LINE_RESET_VALUE;
//        PositionWrapperHelper lastPosition = null;
//        PositionWrapperHelper lastLineStartPosition = null;
//
//        boolean startOfPage = true; // flag to indicate start of page
//        boolean startOfArticle;
//        if (charactersByArticle.size() > 0) {
//            writePageStart();
//        }
//        for (List<TextPosition> textList : charactersByArticle) {
//            if (getSortByPosition()) {
//                TextPositionComparator comparator = new TextPositionComparator();
//                if (useCustomQuickSortHelper) {
//                    QuickSort.sort(textList, comparator);
//                }
//                else {
//                    textList.sort(comparator);
//                }
//            }
//            startArticle();
//            startOfArticle = true;
//
//            List<LineItemHelper> line = new ArrayList<>();
//
//            Iterator<TextPosition> textIter = textList.iterator();
//
//            float previousAveCharWidth = -1;
//            while (textIter.hasNext()) {
//                TextPosition position = textIter.next();
//                PositionWrapperHelper current = new PositionWrapperHelper(position);
//                String characterValue = position.getUnicode();
//
//                if (lastPosition != null && (position.getFont() != lastPosition.getTextPosition().getFont()
//                        || position.getFontSize() != lastPosition.getTextPosition().getFontSize())) {
//                    previousAveCharWidth = -1;
//                }
//
//                float positionX;
//                float positionY;
//                float positionWidth;
//                float positionHeight;
//
//                if (getSortByPosition()) {
//                    positionX = position.getXDirAdj();
//                    positionY = position.getYDirAdj();
//                    positionWidth = position.getWidthDirAdj();
//                    positionHeight = position.getHeightDir();
//                }
//                else {
//                    positionX = position.getX();
//                    positionY = position.getY();
//                    positionWidth = position.getWidth();
//                    positionHeight = position.getHeight();
//                }
//
//                int wordCharCount = position.getIndividualWidths().length;
//
//                float wordSpacing = position.getWidthOfSpace();
//                float deltaSpace;
//                if (wordSpacing == 0 || Float.isNaN(wordSpacing)) {
//                    deltaSpace = Float.MAX_VALUE;
//                }
//                else {
//                    if (lastWordSpacing < 0) {
//                        deltaSpace = wordSpacing * getSpacingTolerance();
//                    }
//                    else {
//                        deltaSpace = (wordSpacing + lastWordSpacing) / 2f * getSpacingTolerance();
//                    }
//                }
//
//                float averageCharWidth;
//                if (previousAveCharWidth < 0) {
//                    averageCharWidth = positionWidth / wordCharCount;
//                }
//                else {
//                    averageCharWidth = (previousAveCharWidth + positionWidth / wordCharCount) / 2f;
//                }
//                float deltaCharWidth = averageCharWidth * getAverageCharTolerance();
//
//                float expectedStartOfNextWordX = EXPECTED_START_OF_NEXT_WORD_X_RESET_VALUE;
//                if (endOfLastTextX != END_OF_LAST_TEXT_X_RESET_VALUE) {
//                    if (deltaCharWidth > deltaSpace) {
//                        expectedStartOfNextWordX = endOfLastTextX + deltaSpace;
//                    }
//                    else {
//                        expectedStartOfNextWordX = endOfLastTextX + deltaCharWidth;
//                    }
//                }
//
//                if (lastPosition != null) {
//                    if (startOfArticle) {
//                        lastPosition.setArticleStart();
//                        startOfArticle = false;
//                    }
//                    if (!overlap(positionY, positionHeight, maxYForLine, maxHeightForLine)) {
//                        writeLine(normalize(line));
//                        line.clear();
//                        lastLineStartPosition = handleLineSeparation(current, lastPosition, lastLineStartPosition, maxHeightForLine);
//                        expectedStartOfNextWordX = EXPECTED_START_OF_NEXT_WORD_X_RESET_VALUE;
//                        maxYForLine = MAX_Y_FOR_LINE_RESET_VALUE;
//                        maxHeightForLine = MAX_HEIGHT_FOR_LINE_RESET_VALUE;
//                        minYTopForLine = MIN_Y_TOP_FOR_LINE_RESET_VALUE;
//                    }
//                    if (expectedStartOfNextWordX != EXPECTED_START_OF_NEXT_WORD_X_RESET_VALUE
//                            && expectedStartOfNextWordX < positionX &&
//                            lastPosition.getTextPosition().getUnicode() != null
//                            && !lastPosition.getTextPosition().getUnicode().endsWith(" ")) {
//                        line.add(LineItemHelper.getWordSeparator());
//                    }
//                }
//                if (positionY >= maxYForLine) {
//                    maxYForLine = positionY;
//                }
//                endOfLastTextX = positionX + positionWidth;
//
//                if (characterValue != null) {
//                    if (startOfPage) {
//                        writeParagraphStart();// not sure this is correct for RTL?
//                    }
//                    line.add(new LineItemHelper(position));
//                }
//                maxHeightForLine = Math.max(maxHeightForLine, positionHeight);
//                minYTopForLine = Math.min(minYTopForLine, positionY - positionHeight);
//                lastPosition = current;
//                if (startOfPage) {
//                    lastPosition.setParagraphStart();
//                    lastPosition.setLineStart();
//                    lastLineStartPosition = lastPosition;
//                    startOfPage = false;
//                }
//                lastWordSpacing = wordSpacing;
//                previousAveCharWidth = averageCharWidth;
//            }
//            if (line.size() > 0) {
//                writeLine(normalize(line));
//                writeParagraphEnd();
//            }
//            endArticle();
//        }
//        writePageEnd();
//    }
//
//    /**
//     * Normalize the given list of TextPositions.
//     *
//     * @param line list of TextPositions
//     * @return a list of strings, one string for every word
//     */
//    private List<WordWithTextPositions> normalize(List<LineItemHelper> line) {
//        List<WordWithTextPositions> normalized = new LinkedList<>();
//        StringBuilder lineBuilder = new StringBuilder();
//        List<TextPosition> wordPositions = new ArrayList<>();
//
//        for (LineItemHelper item : line) {
//            lineBuilder = normalizeAdd(normalized, lineBuilder, wordPositions, item);
//        }
//
//        if (lineBuilder.length() > 0) {
//            normalized.add(createWord(lineBuilder.toString(), wordPositions));
//        }
//        return normalized;
//    }
//
//    /**
//     * Used within {@link #normalize(List)} to handle a {@link TextPosition}.
//     *
//     * @return The StringBuilder that must be used when calling this method.
//     */
//    private StringBuilder normalizeAdd(List<WordWithTextPositions> normalized,
//                                       StringBuilder lineBuilder, List<TextPosition> wordPositions, LineItemHelper item) {
//        if (item.isWordSeparator()) {
//            normalized.add(createWord(lineBuilder.toString(), new ArrayList<>(wordPositions)));
//            lineBuilder = new StringBuilder();
//            wordPositions.clear();
//        }
//        else {
//            TextPosition text = item.getTextPosition();
//            lineBuilder.append(text.getUnicode());
//            wordPositions.add(text);
//        }
//        return lineBuilder;
//    }
//
//    /**
//     * Used within {@link #normalize(List)} to create a single {@link PDFTextStripper.WordWithTextPositions} entry.
//     */
//    private WordWithTextPositions createWord(String word, List<TextPosition> wordPositions) {
//        return new WordWithTextPositions(normalizeWord(word), wordPositions);
//    }
//
//    /**
//     * Normalize certain Unicode characters. For example, convert the single "fi" ligature to "f" and "i". Also
//     * normalises Arabic and Hebrew presentation forms.
//     *
//     * @param word Word to normalize
//     * @return Normalized word
//     */
//    private String normalizeWord(String word) {
//        StringBuilder builder = null;
//        int p = 0;
//        int q = 0;
//        int strLength = word.length();
//        for (; q < strLength; ++q) {
//            char c = word.charAt(q);
//            if (0xFB00 <= c && c <= 0xFDFF || 0xFE70 <= c && c <= 0xFEFF) {
//                if (builder == null) {
//                    builder = new StringBuilder(strLength * 2);
//                }
//                builder.append(word, p, q);
//                if (c == 0xFDF2 && q > 0 && (word.charAt(q - 1) == 0x0627 || word.charAt(q - 1) == 0xFE8D)) {
//                    builder.append("\u0644\u0644\u0647");
//                }
//                else {
//                    builder.append(Normalizer.normalize(word.substring(q, q + 1), Normalizer.Form.NFKC).trim());
//                }
//                p = q + 1;
//            }
//        }
//        if (builder == null) {
//            return handleDirection(word);
//        }
//        else {
//            builder.append(word, p, q);
//            return handleDirection(builder.toString());
//        }
//    }
//
//    /**
//     * Handles the LTR and RTL direction of the given words. The whole implementation stands and falls with the given
//     * word. If the word is a full line, the results will be the best. If the word contains of single words or
//     * characters, the order of the characters in a word or words in a line may wrong, due to RTL and LTR marks and
//     * characters!
//     *
//     * Based on http://www.nesterovsky-bros.com/weblog/2013/07/28/VisualToLogicalConversionInJava.aspx
//     *
//     * @param word The word that shall be processed
//     * @return new word with the correct direction of the containing characters
//     */
//    private String handleDirection(String word) {
//        Bidi bidi = new Bidi(word, Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
//
//        if (!bidi.isMixed() && bidi.getBaseLevel() == Bidi.DIRECTION_LEFT_TO_RIGHT) {
//            return word;
//        }
//
//        int runCount = bidi.getRunCount();
//        byte[] levels = new byte[runCount];
//        Integer[] runs = new Integer[runCount];
//
//        for (int i = 0; i < runCount; i++) {
//            levels[i] = (byte)bidi.getRunLevel(i);
//            runs[i] = i;
//        }
//
//        Bidi.reorderVisually(levels, 0, runs, 0, runCount);
//
//        StringBuilder result = new StringBuilder();
//
//        for (int i = 0; i < runCount; ++i) {
//            int index = runs[i];
//            int start = bidi.getRunStart(index);
//            int end = bidi.getRunLimit(index);
//
//            int level = levels[index];
//
//            if ((level & 1) != 0) {
//                while (--end >= start) {
//                    char character = word.charAt(end);
//                    if (Character.isMirrored(word.codePointAt(end))) {
//                        if (MIRRORING_CHAR_MAP.containsKey(character)) {
//                            result.append(MIRRORING_CHAR_MAP.get(character));
//                        }
//                        else {
//                            result.append(character);
//                        }
//                    }
//                    else {
//                        result.append(character);
//                    }
//                }
//            }
//            else {
//                result.append(word, start, end);
//            }
//        }
//
//        return result.toString();
//    }
//
//    private PositionWrapperHelper handleLineSeparation(PositionWrapperHelper current,
//                                                       PositionWrapperHelper lastPosition,
//                                                       PositionWrapperHelper lastLineStartPosition,
//                                                       float maxHeightForLine) throws IOException {
//        current.setLineStart();
//        isParagraphSeparation(current, lastPosition, lastLineStartPosition, maxHeightForLine);
//        lastLineStartPosition = current;
//        if (current.isParagraphStart()) {
//            if (lastPosition.isArticleStart()) {
//                if (lastPosition.isLineStart()) {
//                    writeLineSeparator();
//                }
//                writeParagraphStart();
//            }
//            else {
//                writeLineSeparator();
//                writeParagraphSeparator();
//            }
//        }
//        else {
//            writeLineSeparator();
//        }
//        return lastLineStartPosition;
//    }
//
//    private void isParagraphSeparation(PositionWrapperHelper position, PositionWrapperHelper lastPosition,
//                                       PositionWrapperHelper lastLineStartPosition, float maxHeightForLine) {
//        boolean result = false;
//        if (lastLineStartPosition == null) {
//            result = true;
//        }
//        else {
//            float yGap = Math.abs(position.getTextPosition().getYDirAdj() - lastPosition.getTextPosition().getYDirAdj());
//            float newYVal = multiplyFloat(getDropThreshold(), maxHeightForLine);
//            float xGap = position.getTextPosition().getXDirAdj() - lastLineStartPosition.getTextPosition().getXDirAdj();
//            float newXVal = multiplyFloat(getIndentThreshold(), position.getTextPosition().getWidthOfSpace());
//            float positionWidth = multiplyFloat(0.25f, position.getTextPosition().getWidth());
//
//            if (yGap > newYVal) {
//                result = true;
//            }
//            else if (xGap > newXVal) {
//                if (!lastLineStartPosition.isParagraphStart()) {
//                    result = true;
//                }
//                else {
//                    position.setHangingIndent();
//                }
//            }
//            else if (xGap < -position.getTextPosition().getWidthOfSpace()) {
//                if (!lastLineStartPosition.isParagraphStart()) {
//                    result = true;
//                }
//            }
//            else if (Math.abs(xGap) < positionWidth) {
//                if (lastLineStartPosition.isHangingIndent()) {
//                    position.setHangingIndent();
//                }
//                else if (lastLineStartPosition.isParagraphStart()) {
//                    Pattern liPattern = matchListItemPattern(lastLineStartPosition);
//                    if (liPattern != null) {
//                        Pattern currentPattern = matchListItemPattern(position);
//                        if (liPattern == currentPattern) {
//                            result = true;
//                        }
//                    }
//                }
//            }
//        }
//        if (result) {
//            position.setParagraphStart();
//        }
//    }
//
//    private Pattern matchListItemPattern(PositionWrapperHelper pw) {
//        TextPosition tp = pw.getTextPosition();
//        String txt = tp.getUnicode();
//        return matchPattern(txt, getListItemPatterns());
//    }
//
//    private float multiplyFloat(float value1, float value2) {
//        return Math.round(value1 * value2 * 1000) / 1000f;
//    }
//
//    private void writeLine(List<WordWithTextPositions> line) throws IOException {
//        int numberOfStrings = line.size();
//        for (int i = 0; i < numberOfStrings; ++i) {
//            WordWithTextPositions word = line.get(i);
//            writeString(word.getText(), word.getTextPositions());
//            if (i < numberOfStrings - 1) {
//                writeWordSeparator();
//            }
//        }
//    }
//
//    private boolean overlap(float y1, float height1, float y2, float height2) {
//        return within(y1, y2) || y2 <= y1 && y2 >= y1 - height1 || y1 <= y2 && y1 >= y2 - height2;
//    }
//
//    private boolean within(float first, float second) {
//        return second < first + .1f && second > first - .1f;
//    }
//
//    /**
//     * Override the default functionality of PDFTextStripper.
//     */
//    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
//        for (TextPosition text : textPositions) {
//            System.out.println("String[" + text.getXDirAdj() + ","
//                    + text.getYDirAdj() + " fs=" + text.getFontSize() + " xscale="
//                    + text.getXScale() + " height=" + text.getHeightDir() + " space="
//                    + text.getWidthOfSpace() + " width="
//                    + text.getWidthDirAdj() + "]" + text.getUnicode());
//        }
//    }
//
//    /**
//     * wrapper of TextPosition that adds flags to track status as linestart and paragraph start positions.
//     * <p>
//     * This is implemented as a wrapper since the TextPosition class doesn't provide complete access to its state fields
//     * to subclasses. Also, conceptually TextPosition is immutable while these flags need to be set post-creation so it
//     * makes sense to put these flags in this separate class.
//     * </p>
//     *
//     * @author m.martinez@ll.mit.edu
//     */
//    private static final class PositionWrapperHelper {
//        private boolean isLineStart = false;
//        private boolean isParagraphStart = false;
//        private boolean isPageBreak = false;
//        private boolean isHangingIndent = false;
//        private boolean isArticleStart = false;
//
//        private TextPosition position = null;
//
//        /**
//         * Constructs a PositionWrapper around the specified TextPosition object.
//         *
//         * @param position the text position.
//         */
//        PositionWrapperHelper(TextPosition position) {
//            this.position = position;
//        }
//
//        /**
//         * Returns the underlying TextPosition object.
//         *
//         * @return the text position
//         */
//        public TextPosition getTextPosition() {
//            return position;
//        }
//
//        public boolean isLineStart() {
//            return isLineStart;
//        }
//
//        /**
//         * Sets the isLineStart() flag to true.
//         */
//        public void setLineStart() {
//            this.isLineStart = true;
//        }
//
//        public boolean isParagraphStart() {
//            return isParagraphStart;
//        }
//
//        /**
//         * sets the isParagraphStart() flag to true.
//         */
//        public void setParagraphStart() {
//            this.isParagraphStart = true;
//        }
//
//        public boolean isArticleStart() {
//            return isArticleStart;
//        }
//
//        /**
//         * Sets the isArticleStart() flag to true.
//         */
//        public void setArticleStart() {
//            this.isArticleStart = true;
//        }
//
//        public boolean isPageBreak() {
//            return isPageBreak;
//        }
//
//        /**
//         * Sets the isPageBreak() flag to true.
//         */
//        public void setPageBreak() {
//            this.isPageBreak = true;
//        }
//
//        public boolean isHangingIndent() {
//            return isHangingIndent;
//        }
//
//        /**
//         * Sets the isHangingIndent() flag to true.
//         */
//        public void setHangingIndent() {
//            this.isHangingIndent = true;
//        }
//    }
//
//    /**
//     * internal marker class. Used as a place holder in a line of TextPositions.
//     */
//    private static final class LineItemHelper {
//        public static LineItemHelper WORD_SEPARATOR = new LineItemHelper();
//
//        public static LineItemHelper getWordSeparator() {
//            return WORD_SEPARATOR;
//        }
//
//        private final TextPosition textPosition;
//
//        private LineItemHelper() {
//            textPosition = null;
//        }
//
//        LineItemHelper(TextPosition textPosition) {
//            this.textPosition = textPosition;
//        }
//
//        public TextPosition getTextPosition() {
//            return textPosition;
//        }
//
//        public boolean isWordSeparator() {
//            return textPosition == null;
//        }
//    }
//
//    private static final class WordWithTextPositions {
//        String text;
//        List<TextPosition> textPositions;
//
//        WordWithTextPositions(String word, List<TextPosition> positions) {
//            text = word;
//            textPositions = positions;
//        }
//
//        public String getText()
//        {
//            return text;
//        }
//
//        public List<TextPosition> getTextPositions()
//        {
//            return textPositions;
//        }
//    }
}