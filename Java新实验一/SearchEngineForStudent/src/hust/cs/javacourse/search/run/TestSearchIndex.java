package hust.cs.javacourse.search.run;

import hust.cs.javacourse.search.index.impl.Term;
import hust.cs.javacourse.search.query.AbstractHit;
import hust.cs.javacourse.search.query.AbstractIndexSearcher;
import hust.cs.javacourse.search.query.impl.IndexSearcher;
import hust.cs.javacourse.search.query.impl.SimpleSorter;
import hust.cs.javacourse.search.util.Config;
import java.io.IOException;
import java.util.Scanner;


public class TestSearchIndex {

    public static void main(String[] args) throws IOException {
        IndexSearcher searcher = new IndexSearcher();
        searcher.open(Config.INDEX_DIR + "index.dat");
        SimpleSorter simpleSorter = new SimpleSorter();
        while (true) {
            System.out.println("请输入查询选项: (输入4退出)");
            System.out.println("1:查询一个单词");
            System.out.println("2：查询两个相邻的单词");
            System.out.println("3：查询两个用关系符连接的单词");
            Scanner scanner = new Scanner(System.in);
            int option = scanner.nextInt();
            if (option == 4) {
                System.out.println("退出！");
                break;
            }
            AbstractHit[] hits;
            switch (option) {
                case 1:
                    String string = scanner.next();
                    hits = searcher.search(new Term(string), simpleSorter);
                    if (hits == null)
                        System.out.println("未搜索到任何结果");
                    else for (AbstractHit hit : hits)
                        System.out.println(hit.toString());
                    break;
                case 2:
                    String string1 = scanner.next(), string2 = scanner.next();
                    hits = searcher.search(new Term(string1), new Term(string2), simpleSorter);
                    if (hits == null) System.out.println("未搜索到任何结果");
                    else for (AbstractHit hit : hits)
                        System.out.println(hit.toString());
                    break;
                case 3:
                    String temp1 = scanner.next(), temp2 = scanner.next(), temp3 = scanner.next();
                    if (temp2.equals("&") || temp2.equals("*")) {
                        hits = searcher.search(new Term(temp1), new Term(temp3),
                                simpleSorter, AbstractIndexSearcher.LogicalCombination.AND);
                        if (hits == null || hits.length < 1) System.out.println("未搜索到任何结果");
                        else for (AbstractHit h : hits)
                            System.out.println(h.toString());

                    } else if (temp2.equals("|") || temp2.equals("+")) {  // 或
                        hits = searcher.search(new Term(temp1), new Term(temp3),
                                simpleSorter, AbstractIndexSearcher.LogicalCombination.OR);
                        if (hits == null || hits.length < 1) System.out.println("未搜索到任何结果");
                        else for (AbstractHit h : hits)
                            System.out.println(h.toString());

                    } else {
                        System.out.println("逻辑关系解析失败");
                        System.out.println("输入格式： word combine word");
                        System.out.println("combine :   or: +,|  and: &,*");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
