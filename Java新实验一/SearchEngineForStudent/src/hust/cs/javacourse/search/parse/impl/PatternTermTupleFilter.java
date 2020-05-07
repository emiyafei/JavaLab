package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.Config;

public class PatternTermTupleFilter extends AbstractTermTupleFilter {
    private String pattern;

    public PatternTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
        pattern = Config.TERM_FILTER_PATTERN;
    }

    /**
     * 获得并过滤下一个三元组
     *
     * @return 正则表达式过滤后的三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null)
            return null;
        while (!termTuple.term.getContent().matches(pattern)) {
            termTuple = input.next();
            if (termTuple == null)
                return null;
        }
        return termTuple;
    }
}
