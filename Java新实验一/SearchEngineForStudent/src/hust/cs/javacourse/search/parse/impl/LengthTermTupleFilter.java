package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;

public class LengthTermTupleFilter extends AbstractTermTupleFilter {
    public LengthTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得并过滤下一个三元组
     *
     * @return 长度过滤后的三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null)
            return null;
        while (termTuple.term.getContent().length() < 3 || termTuple.term.getContent().length() > 20) {
            termTuple = input.next();
            if (termTuple == null)
                return null;
        }
        return termTuple;
    }
}
