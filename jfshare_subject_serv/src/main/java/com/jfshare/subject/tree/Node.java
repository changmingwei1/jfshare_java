package com.jfshare.subject.tree;

import java.io.Serializable;
import java.util.*;


public class Node<T> implements Serializable{
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;

    public Node(){
    }

    public Node(T data){
        this.data = data;
    }

    public Node(T data,Node<T> parent){
        this.data = data;
        this.parent = parent;
    }

    public void setData(T data){
        this.data = data;
    }

    public T getData(){
        return this.data;
    }

    public void setParent(Node<T> parent){
        this.parent = parent;
    }

    public Node<T> getParent(){
        return this.parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public void addToChildren(Node<T> child) {
        if(this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    public List<T> getPath() {
        List<T> path = new ArrayList<>();
        Node<T> node = new Node<>(this.data, this.parent);
        while (node.getParent() != null) {
            path.add(node.getData());
            node = node.getParent();
        }
        Collections.reverse(path);
        return path;
    }
}
