package com.example.myapplication;

import java.io.Serializable;

// storing important information
// useful for undoing the todos
public class EntryTodo {
    private int idx;
    private String content;
    public EntryTodo( String t, int i ) { this.content = t; this.idx = i;}
    public int getIdx ( ) { return this.idx;}
    public String getString () { return this.content;}
}