package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractPosting;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Posting extends AbstractPosting {
    public Posting() {
    }

    public Posting(int docId, int freq, List<Integer> positions) {
        super(docId, freq, positions);
    }

    public boolean equals(Object obj) {
        if(obj instanceof Posting){
            return ((this.docId == ((Posting) obj).docId) &&
                    (this.freq == ((Posting) obj).freq) &&
                    (this.positions.size()==((Posting) obj).positions.size())&&
                    (this.positions.containsAll(((Posting) obj).positions)));
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + this.docId + ", "
                + this.freq + ", "
                + this.positions + "}";
    }

    @Override
    public int getDocId() {
        return this.docId;
    }

    @Override
    public void setDocId(int docId) {
        this.docId = docId;
    }

    @Override
    public int getFreq() {
        return this.freq;
    }

    @Override
    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Override
    public List<Integer> getPositions() {
        return this.positions;
    }

    @Override
    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public int compareTo(AbstractPosting o) {
        return this.docId - o.getDocId();
    }

    @Override
    public void sort() {
        this.positions.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeInt(this.docId);
            out.writeInt(this.freq);
            out.writeObject(this.positions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.docId = in.readInt();
            this.freq = in.readInt();
            this.positions = (ArrayList<Integer>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
