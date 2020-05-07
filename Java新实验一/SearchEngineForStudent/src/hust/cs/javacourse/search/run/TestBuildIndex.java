package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.AbstractIndex;
import hust.cs.javacourse.search.index.AbstractIndexBuilder;
import hust.cs.javacourse.search.index.impl.DocumentBuilder;
import hust.cs.javacourse.search.index.impl.Index;
import hust.cs.javacourse.search.index.impl.IndexBuilder;
import hust.cs.javacourse.search.util.Config;

import java.io.File;
import java.util.Scanner;

public class TestBuildIndex {
    public static void main(String[] args) {
        System.out.println("请输入要执行的操作:\n\t0:从目录中读取索引\n\t1：新建索引\n\t其他：退出\n");
        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        AbstractIndex index;
        switch (op) {
            case 0:
                AbstractIndexBuilder indexBuilder = new IndexBuilder(new DocumentBuilder());
                index = indexBuilder.buildIndex(Config.DOC_DIR);
                System.out.println(index.toString());
                if (index.getDictionary().isEmpty())
                    System.out.println("目录中索引表为空！");
                System.out.println(Config.DOC_DIR);
                break;
            case 1:
                index = new Index();
                index.load(new File(Config.INDEX_DIR + "index.dat"));
                System.out.println(index.toString());
                System.out.println("执行成功！");
                break;
            default:
                System.out.println("退出成功！");
        }

    }
}
