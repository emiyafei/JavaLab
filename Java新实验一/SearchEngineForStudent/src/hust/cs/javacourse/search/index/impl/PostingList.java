package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;
import hust.cs.javacourse.search.index.AbstractPostingList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PostingList extends AbstractPostingList {
    @Override
    public void add(AbstractPosting posting) {
        if (!this.list.contains(posting)) {
            this.list.add(posting);
        }
    }

    @Override
    public String toString() {
        return this.list.toString();
    }

    @Override
    public void add(List<AbstractPosting> postings) {
        for (AbstractPosting posting : postings) {
            if (!this.list.contains(posting)) {
                this.list.add(posting);
            }
        }
    }

    @Override
    public AbstractPosting get(int index) {
        return this.list.get(index);
    }

    @Override
    public int indexOf(AbstractPosting posting) {
        return this.list.indexOf(posting);
    }

    @Override
    public int indexOf(int docId) {
        for (AbstractPosting posting : this.list) {
            if(posting.getDocId()==docId)
                return this.list.indexOf(posting);
        }
        return -1;
    }

    @Override
    public boolean contains(AbstractPosting posting) {
        return this.list.contains(posting);
    }

    @Override
    public void remove(int index) {
        this.list.remove(index);
    }

    @Override
    public void remove(AbstractPosting posting) {
        this.list.remove(posting);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public void sort() {
        this.list.sort(new Comparator<AbstractPosting>() {
            @Override
            public int compare(AbstractPosting o1, AbstractPosting o2) {
                return o1.getDocId() - o2.getDocId();
            }
        });
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.list = (List<AbstractPosting>) in.readObject();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
