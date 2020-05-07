package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.Sort;

import java.util.Comparator;
import java.util.List;

public class SimpleSorter implements Sort {

    public void sort(List<AbstractHit> hits) {
        hits.sort(new Comparator<AbstractHit>() {
            @Override
            public int compare(AbstractHit o1, AbstractHit o2) {
                return -(int) Math.round(score(o1) - score(o2));
            }
        });
    }


    @Override
    public double score(AbstractHit hit) {
        double result = 0;
        for (AbstractPosting posting : hit.getTermPostingMapping().values()) {
            result += posting.getFreq();
        }
        return result;
    }
}
