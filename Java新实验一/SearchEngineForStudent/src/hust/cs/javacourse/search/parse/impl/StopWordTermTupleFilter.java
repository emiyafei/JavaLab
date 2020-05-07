package hust.cs.javacourse.search.parse.impl;

import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleFilter;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.util.StopWords;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWordTermTupleFilter extends AbstractTermTupleFilter {

    private final List<String> stopWordList = new ArrayList<>(Arrays.asList(StopWords.STOP_WORDS));

    public StopWordTermTupleFilter(AbstractTermTupleStream input) {
        super(input);
    }

    /**
     * 获得并过滤下一个三元组
     *
     * @return 停用词过滤后的三元组
     */
    @Override
    public AbstractTermTuple next() {
        AbstractTermTuple termTuple = input.next();
        if (termTuple == null)
            return null;
        while (stopWordList.contains(termTuple.term.getContent())) {    // 是停用词
            termTuple = input.next();
            if (termTuple == null)
                return null;
        }
        return termTuple;
    }
}
