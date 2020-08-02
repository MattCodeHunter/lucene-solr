package org.apache.lucene.demo;

import java.util.Scanner;
import java.nio.file.Paths;
import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;

public class SimpleMetrics {
  private static final String INDEX_PATH = "index";

  private long _doc_freq;
  private long _term_freq;

  public SimpleMetrics(Term term) {
    try {
      Directory idx_dir = FSDirectory.open(Paths.get(INDEX_PATH));
      IndexReader idx_reader = DirectoryReader.open(idx_dir);
      _doc_freq = idx_reader.docFreq(term);
      _term_freq = idx_reader.totalTermFreq(term);
    } catch (IOException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }

  public SimpleMetrics(IndexReader idx_reader, Term term) {
    try {
      _doc_freq = idx_reader.docFreq(term);
      _term_freq = idx_reader.totalTermFreq(term);
    } catch (IOException e) {
      System.err.println("ERROR: " + e.getMessage());
    }
  }

  public long getDocFreq() {
  	return _doc_freq;
  }

  public long getTermFreq() {
  	return _term_freq;
  }

  public static void main(String[] args) {
  	Scanner in = new Scanner(System.in);

  	while (true) {
  	  String line = in.nextLine();
  	  Term term = new Term("contents", line.trim());

  	  SimpleMetrics sm = new SimpleMetrics(term);
  	  System.out.println("doc freq: " + sm.getDocFreq());
  	  System.out.println("term freq: " + sm.getTermFreq());
  	}
  }
}

