package com.webarch.srmobile.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Manoj Khanna
 */

public abstract class Parser {

    protected HashMap<String, ArrayList<ArrayList<String>>> matchListMap;
    private ArrayList<String> sourceLineList = new ArrayList<>(1024);
    private int readIndex;

    public void execute() throws IOException, ParserException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                sourceLineList.add(line);
            }
        }
        bufferedReader.close();

        Block[] blocks = getBlocks();
        matchListMap = new HashMap<>(blocks.length);
        for (int i = 0; i < blocks.length; i++) {
            Block block = blocks[i];
            matchListMap.put(block.name, block.findMatchList(i < blocks.length - 1 ? blocks[i + 1] : null));
        }
    }

    protected abstract InputStream getInputStream() throws IOException;

    protected abstract Block[] getBlocks();

    private String readSourceLine() {
        return readIndex < sourceLineList.size() ? sourceLineList.get(readIndex++) : null;
    }

    protected class Block {

        private String name;
        private Pattern[] patterns;
        private int matchCount;

        public Block(String name, String[] patternStrings) {
            this(name, patternStrings, 1);
        }

        public Block(String name, String[] patternStrings, int matchCount) {
            this.name = name;

            patterns = new Pattern[patternStrings.length];
            for (int i = 0; i < patternStrings.length; i++) {
                patterns[i] = Pattern.compile(patternStrings[i]);
            }

            this.matchCount = matchCount;
        }

        private ArrayList<ArrayList<String>> findMatchList(Block nextBlock) throws ParserException {
            ArrayList<ArrayList<String>> matchList = new ArrayList<>();
            if (matchCount == -1) {
                while (true) {
                    ArrayList<String> match;
                    try {
                        match = findMatch();
                    } catch (ParserException ex) {
                        if (!matchList.isEmpty()) {
                            break;
                        } else {
                            throw ex;
                        }
                    }

                    if (match != null) {
                        matchList.add(match);
                    } else if (nextBlock != null && !matchList.isEmpty() && nextBlock.findMatch() != null) {
                        break;
                    } else {
                        readIndex++;
                    }
                }
            } else {
                for (int i = 1; i <= matchCount; i++) {
                    ArrayList<String> match = findMatch();
                    if (match != null) {
                        matchList.add(match);
                    } else {
                        readIndex++;
                        i--;
                    }
                }
            }
            return matchList;
        }

        private ArrayList<String> findMatch() throws ParserException {
            ArrayList<String> match = new ArrayList<>();
            for (int i = 0; i < patterns.length; i++) {
                String line = readSourceLine();
                if (line == null) {
                    throw new ParserException(Parser.this, name);
                }

                Matcher matcher = patterns[i].matcher(line);
                if (!matcher.find()) {
                    readIndex -= i + 1;
                    return null;
                }

                for (int j = 1, n = matcher.groupCount(); j <= n; j++) {
                    match.add(matcher.group(j).trim());
                }
            }
            modifyMatch(match);
            return match;
        }

        protected void modifyMatch(ArrayList<String> match) {
        }

    }

}
