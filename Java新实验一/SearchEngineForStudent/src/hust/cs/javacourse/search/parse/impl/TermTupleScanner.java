package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.index.impl.TermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleScanner;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.StringSplitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class TermTupleScanner extends AbstractTermTupleScanner {
    private int position;
    private LinkedList<String> list;
    StringSplitter splitter = new StringSplitter();

    public TermTupleScanner(){
        this.position = 0;
        list = new LinkedList<String>();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    public TermTupleScanner(BufferedReader input){
        super(input);
        this.position = 0;
        list = new LinkedList<String>();
        splitter.setSplitRegex(Config.STRING_SPLITTER_REGEX);
    }

    @Override
    public AbstractTermTuple next() {
        try {
            if(list.isEmpty()){
                String line = this.input.readLine();
                if(line == null) return null;
                while (line!=null&&line.length()==0)
                    line = this.input.readLine();
                if(line == null) return null;
                list = new LinkedList<String>(splitter.splitByRegex(line));
                if(list.isEmpty()) return null;
            }
            String word = list.removeFirst().toLowerCase();
            return new TermTuple(word, this.position++);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
