package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractDocument;
import hust.cs.javacourse.search.index.AbstractDocumentBuilder;
import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.util.Config;
import hust.cs.javacourse.search.util.FileUtil;

import java.io.File;

public class IndexBuilder extends AbstractIndexBuilder {
    private int numbers;

    public IndexBuilder(AbstractDocumentBuilder docBuilder) {
        super(docBuilder);
        numbers = 0;
    }
    @Override
    public AbstractIndex buildIndex(String rootDirectory) {
        AbstractIndex index = new Index();
        for(String path: FileUtil.list(rootDirectory)){
            AbstractDocument doc = this.docBuilder.build(numbers++, path, new File(path));
            index.addDocument(doc);
        }
        File file = new File(Config.INDEX_DIR + "index.dat");
        index.save(file);
        return index;
    }

}
