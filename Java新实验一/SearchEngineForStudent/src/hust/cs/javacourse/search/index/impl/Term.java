package hust.cs.javacourse.search.index.impl;

import hust.cs.javacourse.search.index.AbstractTerm;

import java.io.*;

public class Term extends AbstractTerm {
    public Term() {
    }

    public Term(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AbstractTerm){
            if(this.getContent()==null&&((AbstractTerm) obj).getContent().equals("null"))
                return true;
            return this.content.equals(((AbstractTerm) obj).getContent());
        }
        return false;
    }

    @Override
    public String toString() {
        return this.content;
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
    public int compareTo(AbstractTerm o) {
        return this.content.compareTo(o.getContent());
    }

    @Override
    public void writeObject(ObjectOutputStream out) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));
            writer.print(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public void readObject(ObjectInputStream in) {
        String s = null;
        BufferedReader reader = null;
        try {
            StringBuffer buf = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(in));
            while ((s = reader.readLine()) != null) {
                buf.append(s).append("\n");
            }
            s = buf.toString().trim();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        this.content = s;
    }
}
