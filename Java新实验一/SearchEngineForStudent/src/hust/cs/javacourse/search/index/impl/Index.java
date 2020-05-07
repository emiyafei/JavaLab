package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.*;

import java.io.*;
import java.util.*;


public class Index extends AbstractIndex implements FileSerializable {

    @Override
    public String toString() {
        return this.termToPostingListMapping.toString();
    }


    @Override
    public void addDocument(AbstractDocument document) {
        Map<AbstractTerm, List<Integer>> map = new HashMap<AbstractTerm, List<Integer>>();
        for (AbstractTermTuple tuple : document.getTuples()) {
            if (map.get(tuple.term) == null)
                map.put(tuple.term, new ArrayList<Integer>());
            map.get(tuple.term).add(tuple.curPos);
        }
        for (AbstractTerm term : map.keySet()) {
            if (this.termToPostingListMapping.get(term) == null)
                this.termToPostingListMapping.put(term, new PostingList());
            this.termToPostingListMapping.get(term).add(new Posting(document.getDocId(), map.get(term).size(), map.get(term)));
        }
        this.docIdToDocPathMapping.put(document.getDocId(), document.getDocPath());
    }


    @Override
    public void load(File file) {
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
            readObject(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void save(File file) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
            writeObject(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public AbstractPostingList search(AbstractTerm term) {
        return this.termToPostingListMapping.get(term);
    }


    @Override
    public Set<AbstractTerm> getDictionary() {
        return new HashSet<>(this.termToPostingListMapping.keySet());
    }


    @Override
    public void optimize() {
        for (AbstractPostingList list : this.termToPostingListMapping.values()) {
            list.sort();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).sort();
            }
        }
    }

    @Override
    public String getDocName(int docId) {
        return this.docIdToDocPathMapping.get(docId);
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        try {
            out.writeObject(this.termToPostingListMapping);
            out.writeObject(this.docIdToDocPathMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        try {
            this.termToPostingListMapping = (Map<AbstractTerm, AbstractPostingList>) in.readObject();
            this.docIdToDocPathMapping = (Map<Integer, String>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
