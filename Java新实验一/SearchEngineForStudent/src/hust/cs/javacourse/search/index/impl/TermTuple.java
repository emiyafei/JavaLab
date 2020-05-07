package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;

public class TermTuple extends AbstractTermTuple {
    public TermTuple() {
    }

    public TermTuple(AbstractTerm term, int curPos) {
        this.term = term;
        this.curPos = curPos;
    }

    public TermTuple(String content, int curPos) {
        this.term = new Term();
        this.term.setContent(content);
        this.curPos = curPos;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TermTuple) {
            return (this.term.equals(((TermTuple) obj).term) &&
                    this.curPos == ((TermTuple) obj).curPos);
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + this.term.toString() + ", "
                + this.freq + ", "
                + this.curPos + ")";
    }
}
