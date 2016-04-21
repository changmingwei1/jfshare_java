package com.jfshare.subject.tree;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree<T> {
    //树map结构，查找方便
    private Map<Integer, Node<T>> treeMap = new HashMap<>();

    public Tree() {
    }

    public Map<Integer, Node<T>> getTreeMap() {
        return treeMap;
    }

    public void setTreeMap(Map<Integer, Node<T>> treeMap) {
        this.treeMap = treeMap;
    }

    //添加一个节点，并指明父节点
    public void add(Node<T> node, Node<T> parent) {
        node.setParent(parent);
        parent.addToChildren(node);
    }

    /**
     * 查找节点
     * @param id
     * @return
     */
    public Node<T> findNode(Integer id) {
        return treeMap.get(id);
    }


    /**
     * 查节点下所有的叶子节点
     * @param node
     * @param objs
     * @return
     */
    public void getLeaves(Node<T> node, List<T> objs) {
        if(node != null) {
            if(CollectionUtils.isNotEmpty(node.getChildren())) {
                for(Node<T> n : node.getChildren()) {
                    getLeaves(n, objs);
                }
            } else {
                objs.add(node.getData());
            }
        }
    }
}
