package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractTermTuple;
import hust.cs.javacourse.search.parse.AbstractTermTupleStream;
import hust.cs.javacourse.search.parse.impl.LengthTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.PatternTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.StopWordTermTupleFilter;
import hust.cs.javacourse.search.parse.impl.TermTupleScanner;

import java.io.*;

public class DocumentBuilder extends AbstractDocumentBuilder {
    @Override
    public AbstractDocument build(int docId, String docPath, AbstractTermTupleStream termTupleStream) {
        AbstractDocument doc = new Document(docId, docPath);
        AbstractTermTuple tuple = termTupleStream.next();
        while (tuple != null) {
            doc.addTuple(tuple);
            tuple = termTupleStream.next();
        }
        return doc;
    }

    @Override
    public AbstractDocument build(int docId, String docPath, File file) {
        AbstractTermTupleStream termTupleStream = null;
        try {
            termTupleStream =
                    new LengthTermTupleFilter(new PatternTermTupleFilter(new StopWordTermTupleFilter(
                            new TermTupleScanner(new BufferedReader(new InputStreamReader( new FileInputStream(file)))))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.build(docId, docPath, termTupleStream);
    }
}
