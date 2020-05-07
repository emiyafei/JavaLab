package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.index.impl.Posting;
import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.Sort;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexSearcher extends AbstractIndexSearcher {

    @Override
    public void open(String indexFile) {
        this.index.load(new File(indexFile));
        this.index.optimize();
    }

    /**
     * 根据单个检索词进行搜索
     *
     * @param queryTerm ：检索词
     * @param sorter    ：排序器
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm, Sort sorter) {
        AbstractPostingList postList = index.search(queryTerm);
        if (postList == null)
            return null;
        List<AbstractHit> hits = new ArrayList<>();
        for (int i = 0; i < postList.size(); i++) {
            AbstractPosting post = postList.get(i);
            String path = index.getDocName(post.getDocId());
            Map<AbstractTerm, AbstractPosting> map = new HashMap<>();
            map.put(queryTerm, post);
            AbstractHit hit = new Hit(post.getDocId(), path, map);
            hit.setScore(sorter.score(hit));
            hits.add(hit);
        }
        sorter.sort(hits);
        return hits.toArray(new AbstractHit[0]);
    }

    /**
     * 根据二个检索词进行搜索
     *
     * @param queryTerm1 ：第1个检索词
     * @param queryTerm2 ：第2个检索词
     * @param sorter     ：    排序器
     * @param combine    ：   多个检索词的逻辑组合方式
     * @return ：命中结果数组
     */
    @Override
    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter, LogicalCombination combine) {
        AbstractPostingList postList1 = index.search(queryTerm1);
        AbstractPostingList postList2 = index.search(queryTerm2);
        List<AbstractHit> hits = new ArrayList<>();
        int i = 0, j = 0;
        int size1 = (postList1 == null ? 0 : postList1.size()), size2 = (postList2 == null ? 0 : postList2.size());
        while (i < size1 && j < size2) {
            AbstractPosting post1 = postList1.get(i);
            AbstractPosting post2 = postList2.get(j);
            if (post1.getDocId() == post2.getDocId()) {
                String path = index.getDocName(post1.getDocId());
                Map<AbstractTerm, AbstractPosting> map = new HashMap<>();
                map.put(queryTerm1, post1);
                map.put(queryTerm2, post2);
                AbstractHit hit = new Hit(post1.getDocId(), path, map);
                hit.setScore(sorter.score(hit));
                hits.add(hit);
                i++;
                j++;
            } else if (post1.getDocId() < post2.getDocId()) {
                i = addHit(queryTerm1, sorter, combine, hits, i, post1);
            } else {
                j = addHit(queryTerm2, sorter, combine, hits, j, post2);
            }
        }
        if (combine == LogicalCombination.OR) {
            while (i < size1) {
                AbstractPosting post1 = postList1.get(i);
                i = addHit(queryTerm1, sorter, combine, hits, i, post1);
            }
            while (j < size2) {
                AbstractPosting post2 = postList2.get(j);
                j = addHit(queryTerm2, sorter, combine, hits, j, post2);
            }
        }
        new SimpleSorter().sort(hits);
        return hits.toArray(new Hit[0]);
    }

    public int addHit(AbstractTerm queryTerm1, Sort sorter, LogicalCombination combine, List<AbstractHit> hits, int i, AbstractPosting post1) {
        if (combine == LogicalCombination.OR) {
            String path = index.getDocName(post1.getDocId());
            Map<AbstractTerm, AbstractPosting> map = new HashMap<>();
            map.put(queryTerm1, post1);
            AbstractHit hit = new Hit(post1.getDocId(), path, map);
            hit.setScore(sorter.score(hit));
            hits.add(hit);
        }
        i++;
        return i;
    }

    public AbstractHit[] search(AbstractTerm queryTerm1, AbstractTerm queryTerm2, Sort sorter) {
        AbstractPostingList postList1 = index.search(queryTerm1);
        AbstractPostingList postList2 = index.search(queryTerm2);
        if (postList1 == null || postList2 == null)
            return null;
        List<AbstractHit> hits = new ArrayList<>();
        int i = 0, j = 0;
        while (i < postList1.size() && j < postList2.size()) {
            AbstractPosting post1 = postList1.get(i);
            AbstractPosting post2 = postList2.get(j);
            if (post1.getDocId() == post2.getDocId()) {
                List<Integer> position1 = post1.getPositions();
                List<Integer> position2 = post2.getPositions();
                int i1 = 0, i2 = 0;
                List<Integer> positions = new ArrayList<>();
                while (i1 < position1.size() && i2 < position2.size()) {
                    int p1 = position1.get(i1);
                    int p2 = position2.get(i2);
                    if (p1 == p2 - 1) {
                        positions.add(p1);
                        i1++;
                        i2++;
                    } else if (p1 < p2 - 1) {
                        i1++;
                    } else {
                        i2++;
                    }
                }
                if (positions.size() > 0) {
                    String path = index.getDocName(post1.getDocId());
                    Map<AbstractTerm, AbstractPosting> map = new HashMap<>();
                    map.put(new Term(queryTerm1.getContent() + " " + queryTerm2.getContent()),
                            new Posting(post1.getDocId(), positions.size(), positions));
                    AbstractHit hit = new Hit(post1.getDocId(), path, map);
                    hit.setScore(sorter.score(hit));        // 先设置分数
                    hits.add(hit);
                }
                i++;
                j++;
            } else if (post1.getDocId() < post2.getDocId()) {
                i++;
            } else {
                j++;
            }
        }
        if (hits.size() < 1) return null;
        new SimpleSorter().sort(hits);
        return hits.toArray(new Hit[0]);
    }
}