package ru.vood.admplugin.dialogs.ExtSwing;

import ru.vood.admplugin.dialogs.ExtSwing.treeWhithCheckBox.CheckNode;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdObjectEntityService;
import ru.vood.core.runtime.exception.ApplicationErrorException;

import javax.persistence.PersistenceException;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ВАЖНО - работа с уже готовым деревом может производится только через модель дерева.
 * Только в этом случае гарантируется правильная работа и вызов событий
 * В противном случае новые узлы могут быть не прорисованы
 * <p/>
 * show JTtree from ResultSet.
 *
 * @see JTree
 */

public class JDBTree extends JTree {
    private static JDBTree tree;
    //private static boolean loaded = false;
    //private ResultSet _r = null;

    public JDBTree() {
        super();
        this.setRootVisible(false);
        //this.loadTree();
    }

    private static JDBTree getInstance() {
        if (tree == null) {
            tree = new JDBTree();
        }
        return tree;
    }

    /**
     * Добавляет в дерево класс ac, место куда нужно его добавть ищется по свойству родительский класс
     *
     * @param bdObject
     */
   /* private void addToTree(VBdObjectEntity bdObject) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();

        DefaultMutableTreeNode node = ((DefaultMutableTreeNode) model.getRoot());
        while (node.getNextNode() != null && node.getUserObject() != bdObject.getParent()) {
            node = (DefaultMutableTreeNode) node.getNextNode();
        }
        DefaultMutableTreeNode sel = node;
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(bdObject);
        model.insertNodeInto(tmp, sel, sel.getChildCount());

        model.reload();
    }*/
    public void refresh(boolean onlyTable) {
        loadTree(onlyTable);
        updateUI();
    }

    public void loadTree(boolean onlyTable) {
        VBdObjectEntityService bdObjectEntityService = LoadedCTX.getService(VBdObjectEntityService.class);
        String[] typeObjectCodeS;
        if (onlyTable) {
            typeObjectCodeS = new String[]{"TABLE", "REFERENCE", "ARRAY", "STRING", "NUMBER", "DATE", "BOOLEAN", "OBJECT"};
        } else {
            typeObjectCodeS = new String[]{"TABLE", "REFERENCE", "ARRAY", "STRING", "NUMBER", "DATE", "BOOLEAN", "OBJECT", "COLOMN", "INDEX"};
        }

        ArrayList<VBdObjectEntity> bdObjects = null;
        try {
            bdObjects = (ArrayList<VBdObjectEntity>) bdObjectEntityService.findByTypeObjectCodeIn(typeObjectCodeS);
            loadList(bdObjects, onlyTable);
        } catch (PersistenceException e) {
            throw new ApplicationErrorException("Не удалось загрузить дерево");
        }


    }

    public void loadList(ArrayList<VBdObjectEntity> entityList) {
        loadList(entityList, false);
    }

    private void loadList(ArrayList<VBdObjectEntity> entityList, boolean onlyTable) {
        //TreeMap<BigDecimal, DefaultMutableTreeNode> nodeTreeMap = new TreeMap<>();
        NodeMap nodeTreeMap = new NodeMap();
        DefaultTreeModel model = (DefaultTreeModel) this.getModel();

        for (VBdObjectEntity entity : entityList) {
            model = put(nodeTreeMap, entity, onlyTable, model);
        }

        this.setModel(model);


        System.out.println(this.getModel());
    }

    private DefaultTreeModel put(NodeMap nodeTreeMap, VBdObjectEntity entity, boolean onlyTable, DefaultTreeModel model) {

        Object treeObj;
        if (onlyTable) {
            treeObj = entity;
        } else {
            treeObj = new CheckNode(entity);
        }
        if (!nodeTreeMap.containsKey(getKey(entity))) {
            if (entity.getParent() != null) {
                if (!nodeTreeMap.containsKey(getParentKey(entity))) {
                    model = put(nodeTreeMap, entity.getParent(), onlyTable, model);
                }
                DefaultMutableTreeNode sel = nodeTreeMap.get(getParentKey(entity));
                DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(treeObj);
                model.insertNodeInto(tmp, sel, sel.getChildCount());
                model.nodeStructureChanged(sel);
                nodeTreeMap.put(getKey(entity), tmp);
            } else { //добавление корневых элементов
                //добавление корневых элементов
                DefaultMutableTreeNode sel = new DefaultMutableTreeNode(treeObj);
                model = new DefaultTreeModel(sel);
                this.setModel(model);
                this.setRootVisible(true);
                model.nodeStructureChanged(sel);
                nodeTreeMap.put(getKey(entity), sel);
            }
        }

        return model;
    }

    private String getKey(VBdObjectEntity entity) {
        return (entity.getParent() == null ? "NULL" : entity.getParent().getCode()) + "->" + entity.getCode();
    }

    private String getParentKey(VBdObjectEntity entity) {
        return (entity.getParent().getParent() == null ? "NULL" : entity.getParent().getParent().getCode()) + "->" + entity.getParent().getCode();
    }

    private void putWhenParenNotNull(NodeMap nodeTreeMap, VBdObjectEntity entity, DefaultTreeModel model, Object obj) {
        DefaultMutableTreeNode sel = nodeTreeMap.get(getParentKey(entity));
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(obj);
        model.insertNodeInto(tmp, sel, sel.getChildCount());
        model.nodeStructureChanged(sel);
        nodeTreeMap.put(getKey(entity), tmp);
    }

    private DefaultTreeModel putWhenParenNull(NodeMap nodeTreeMap, VBdObjectEntity entity, Object obj) {
        //добавление корневых элементов
        DefaultMutableTreeNode sel = new DefaultMutableTreeNode(obj);
        DefaultTreeModel model = new DefaultTreeModel(sel);
        this.setModel(model);
        this.setRootVisible(true);
        model.nodeStructureChanged(sel);
        nodeTreeMap.put(getKey(entity), sel);
        return model;
    }

    public void addToTree(VBdObjectEntity bdTable) {
        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) model.getRoot();
        while (defaultMutableTreeNode != null) {
            if (bdTable.getParent() != null && bdTable.getParent().equals(defaultMutableTreeNode.getUserObject())) {
                DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(bdTable);
                model.insertNodeInto(tmp, defaultMutableTreeNode, defaultMutableTreeNode.getChildCount());
                model.nodeChanged(tmp);
                break;
            }
            defaultMutableTreeNode = defaultMutableTreeNode.getNextNode();
        }
    }

    public void gotoObjectOnTree(VBdObjectEntity entity) {
        DefaultTreeModel model = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) model.getRoot();
        while (defaultMutableTreeNode != null) {
            if (defaultMutableTreeNode.getUserObject().equals(entity)) {
                break;
            }
            defaultMutableTreeNode = defaultMutableTreeNode.getNextNode();
        }

        TreeNode[] nodes = ((DefaultTreeModel) tree.getModel()).getPathToRoot(defaultMutableTreeNode);
        TreePath tpath = new TreePath(nodes);
        tree.setSelectionPath(tpath);
    }

    private class NodeMap extends HashMap<String, DefaultMutableTreeNode> {
    }


}