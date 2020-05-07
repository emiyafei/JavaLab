package hust.cs.javacourse.search.query.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractTerm;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.util.FileUtil;

import java.util.Map;

public class Hit extends AbstractHit {

    public Hit(){}


    public Hit(int docId, String docPath){
        super(docId, docPath);
    }

    public Hit(int docId, String docPath, Map<AbstractTerm, AbstractPosting> termPostingMapping){
        super(docId, docPath, termPostingMapping);
    }

    @Override
    public int getDocId() {
        return this.docId;
    }

    @Override
    public String getDocPath() {
        return this.docPath;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public double getScore() {
        return this.score;
    }

    @Override
    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public Map<AbstractTerm, AbstractPosting> getTermPostingMapping() {
        return this.termPostingMapping;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.docPath).append(",\n");
        buf.append(this.content).append(",\n");
        buf.append(this.termPostingMapping.keySet().iterator().next().toString()).append(",\n");;
        buf.append(this.termPostingMapping.values().iterator().next().toString()).append(",\n");;
        return buf.toString();
    }

    @Override
    public int compareTo(AbstractHit o) {
        return (int) Math.round(this.score - o.getScore());
    }
}
