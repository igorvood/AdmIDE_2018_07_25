package ru.vood.admplugin.dialogs;

import ru.vood.admplugin.dialogs.ExtSwing.*;
import ru.vood.admplugin.infrastructure.generateCode.impl.TypeOfGenClass;
import ru.vood.admplugin.infrastructure.generateCode.impl.createFiles.GenerateFiles;
import ru.vood.admplugin.infrastructure.spring.context.LoadedCTX;
import ru.vood.admplugin.infrastructure.spring.entity.VBdColumnsEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;
import ru.vood.admplugin.infrastructure.spring.entity.VBdTableEntity;
import ru.vood.admplugin.infrastructure.spring.intf.VBdTableEntityService;
import ru.vood.admplugin.infrastructure.spring.referenceBook.ObjectTypes;
import ru.vood.admplugin.infrastructure.spring.referenceBook.RootObjects;
import ru.vood.admplugin.infrastructure.sql.additionalSteps.oracle.stepFirstLoad.TuneChainStepsFirstLoad;
import ru.vood.admplugin.infrastructure.tune.PluginTunes;
import ru.vood.core.runtime.exception.ApplicationErrorException;
import ru.vood.core.runtime.exception.CoreRuntimeException;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ADMDialog extends JAddDialog {
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuFileExit;
    private JMenuItem menuFileTune;
    private JMenuItem menuFileSave;
    private JMenuItem menuFileLoad;
    private JMenuItem menuFileLoadOrSave;
    private JMenu menuTools;
    private JMenuItem menuFileToolsRefresh;
    private JMenuItem menuFileToosFirstLoad;

    private JPopupMenu jPopupMenu;
    private JMenuItem jMenuItemAddSubType;
    private JMenuItem jMenuItemEditType;
    private JMenuItem jMenuItemAddToList;

    private JPopupMenu jPopupMenuTable;
    private JMenu menuAdd;
    private JMenuItem jMenuItemAddColumn;


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JDBTree tree1;
    private JTable columnTable;
    private JProgressBar progressBar1;
    private JLabel shortName;
    private JScrollPane columnsPanel;
    private JScrollPane indexesPanel;
    private JTable indexTable;

    public ADMDialog() {
        //createUIComponents();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        tree1.addTreeSelectionListener(new TreeSelectionListener() {
            /**
             * Called whenever the value of the selection changes.
             *
             * @param e the event that characterizes the change.
             */
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                try {
                    if (tree1.getLastSelectedPathComponent() != null && ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject() != null) {
                        ((JDBTableColumnModel) columnTable.getModel()).clear();

                        VBdObjectEntity entity = (VBdObjectEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject();
                        shortName.setText(entity.getCode());
                        if (entity instanceof VBdTableEntity) {
                            ((JDBTableColumnModel) columnTable.getModel()).loadTableByObj((VBdTableEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject());
                            columnTable.updateUI();

                            ((JDBTableIndexsModel) indexTable.getModel()).loadTableByObj((VBdTableEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject());
                            indexTable.updateUI();

                        }
                    }

                } catch (CoreRuntimeException qw) {
                    System.out.println(qw.toString());
                    qw.printStackTrace();
                }
            }
        });
        columnTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable obj = (JTable) e.getSource();
                    //если клинул по наименованию типа
                    if (obj.getSelectedColumn() == 2) {
                        JDBTableColumnModel tableModel = (JDBTableColumnModel) obj.getModel();
                        tree1.gotoObjectOnTree(tableModel.getSelectedTypeObject(obj.getSelectedRow()));
                        tree1.getLastSelectedPathComponent();
                    }
                }
                super.mouseClicked(e);
            }
        });
        tree1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("=======================");
                System.out.println(e);
                System.out.println((short) e.getKeyChar());
                super.keyTyped(e);
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    @Override
    protected void extension() {
        this.setSize(new Dimension(1200, 550));
        workMenuBar();
        workPopupMenu();
        workTree();
    }

    private void workMenuBar() {
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuFile.setText("File");
        {
            menuFileSave = new JMenuItem();
            menuFileSave.setText("Save");
            menuFileSave.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    fileTune_ActionPerformed(ae);
                }

                private void fileTune_ActionPerformed(ActionEvent ae) {

                    PluginTunes pluginTunes = LoadedCTX.getService(PluginTunes.class);
                    String defaultFolder = pluginTunes.getDefaultFolder();
                    File file;
                    JFileChooser fileopen = new JFileChooser(defaultFolder);
                    fileopen.setDialogType(JFileChooser.SAVE_DIALOG);
                    int ret = fileopen.showDialog(null, "Save File");
                    if (ret == JFileChooser.APPROVE_OPTION) {
                        file = fileopen.getSelectedFile();
                        //WorkWithTunes.getInstance().setTuneValue(ListTunes.DEFAULT_FOLDER.getName(), file.getParent());
                        //ExportInterface save = new Save();
                        //Todo кусок для проверки, удалить его
/*
                        ExportObjects exportObjects = new ExportObjects();
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1.getModel().getRoot();
                        while (node.getNextNode() != null) {
                            VBdObjectEntity bdObject = (VBdObjectEntity) node.getUserObject();
                            exportObjects.set(new Varchar2(bdObject.getId().toString()), bdObject);
                            node = node.getNextNode();
                        }
*/

                        //
                        //save.putOut(exportObjects, file, null);

                    }

                    /*File f = new File("Tunes.xml");
                    ADMTuneDialog tuneDialog = new ADMTuneDialog(f);
                    tuneDialog.pack();
                    tuneDialog.setVisible(true);*/
                    //todo Пока это действие не закодировано, реализовать
                    System.out.println("Пока это действие не закодировано");
                }
            });
            menuFile.add(menuFileSave);

            menuFileLoadOrSave = new JMenuItem();
            menuFileLoadOrSave.setText("Load/Save");
            menuFileLoadOrSave.addActionListener(ae -> {
                JAddDialog dialog = new SelectedDialog(null);
                dialog.pack();
                dialog.setVisible(true);
            });
            menuFile.add(menuFileLoadOrSave);


            menuFileLoad = new JMenuItem();
            menuFileLoad.setText("Load");
            menuFileLoad.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    fileTune_ActionPerformed(ae);
                }

                private void fileTune_ActionPerformed(ActionEvent ae) {
                    /*File f = new File("Tunes.xml");
                    ADMTuneDialog tuneDialog = new ADMTuneDialog(f);
                    tuneDialog.pack();
                    tuneDialog.setVisible(true);*/
                    //todo Пока это действие не закодировано, реализовать
                    System.out.println("Пока это действие не закодировано");
                }
            });
            menuFile.add(menuFileLoad);


            menuFileTune = new JMenuItem();
            menuFileTune.setText("Tune");
            menuFileTune.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    fileTune_ActionPerformed(ae);
                }

                private void fileTune_ActionPerformed(ActionEvent ae) {
                    throw new ApplicationErrorException("Изменение конфигурации на лету не возможною");
//                    File f = new File("Tunes.xml");
//                    ADMTuneDialog tuneDialog = new ADMTuneDialog(f);
//                    tuneDialog.pack();
//                    tuneDialog.setVisible(true);
                }
            });
            menuFile.add(menuFileTune);

            menuFileExit = new JMenuItem();
            menuFileExit.setText("Exit");
            menuFileExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    fileExit_ActionPerformed(ae);
                }

                private void fileExit_ActionPerformed(ActionEvent ae) {
                    onCancel();
                }
            });
            menuFile.add(menuFileExit);
        }

        menuTools = new JMenu();
        menuTools.setText("Tools");
        {
            menuFileToolsRefresh = new JMenuItem();
            menuFileToolsRefresh.setText("Refresh");
            /*  private void FirstLoad_ActionPerformed(ActionEvent ae) {
                  try {
                      LoadedCTX.getService(TuneChainStepsFirstLoad.class).run();
                      workTree();
                  } catch (CoreExeption coreExeption) {
                      new MessageWin("Не удалось инициализировать таблицы", coreExeption);
                  }

              }*/
            menuFileToolsRefresh.addActionListener(ae -> {
                try {
                    tree1.refresh(true);
                } catch (CoreRuntimeException e) {
                    new ErrWin(null, "Обновление структуры данных не удалось.", true, "Обновление структуры данных не удалось. \n" + e.getErrorMessage(), e);
                }
            });
            menuTools.add(menuFileToolsRefresh);
        }

        {
            menuFileToosFirstLoad = new JMenuItem();
            menuFileToosFirstLoad.setText("First Load");
            menuFileToosFirstLoad.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        FirstLoad_ActionPerformed(ae);
                    } catch (CoreRuntimeException e) {
                        new ErrWin(null, "Первоначальная загрузка не удалась.", true, "Первоначальная загрузка не удалась. " + e.getErrorMessage(), e);
                    }
                }

                private void FirstLoad_ActionPerformed(ActionEvent ae) {
                        LoadedCTX.getService(TuneChainStepsFirstLoad.class).run();
                        workTree();
                }
            });
            menuTools.add(menuFileToosFirstLoad);
        }

        {
            JMenuItem menuGenerateCode = new JMenuItem();
            menuGenerateCode.setText("Generate Code");
            /*  private void FirstLoad_ActionPerformed(ActionEvent ae) {
                  try {
                      LoadedCTX.getService(TuneChainStepsFirstLoad.class).run();
                      workTree();
                  } catch (CoreExeption coreExeption) {
                      new MessageWin("Не удалось инициализировать таблицы", coreExeption);
                  }

              }*/
            menuGenerateCode.addActionListener(ae -> {

                final VBdTableEntityService vBdTableEntityService = LoadedCTX.getService(VBdTableEntityService.class);
                final List<VBdTableEntity> tables = vBdTableEntityService.findByTypeObjectCodeIn("TABLE");
                final GenerateFiles generateFiles = LoadedCTX.getService(GenerateFiles.class);

                final List<Path> pathList = generateFiles.genFiles(Paths.get("C:\\temp\\test\\demo\\src\\main\\kotlin"), tables, TypeOfGenClass.values());
                System.out.println(pathList);
            });
            menuTools.add(menuGenerateCode);
        }

        this.setJMenuBar(menuBar);
        menuBar.add(menuFile);
        menuBar.add(menuTools);
    }

    /*private void createUIComponents() {
        //table1 = new JTable(new DBTableModel());
        s = JDBTree.getInstance();
        tree1.setCellRenderer(new DBTreeCellRenderer());

    }*/

    private void workTree() {
        try {
            tree1.loadTree(true);
            tree1.setCellRenderer(new DBTreeCellRenderer());
        } catch (ApplicationErrorException exception) {
            exception.printStackTrace();
            System.out.println(this.getClass() + " Необходимо загрузить талицы. ");
            new MessageWin("Необходимо загрузить талицы. " + exception.getMessage());

        }
    }

    private void workPopupMenu() {
        jPopupMenu = new JPopupMenu();
        {
            jMenuItemAddSubType = new JMenuItem();
            jMenuItemAddSubType.setText("Add");
            jMenuItemAddSubType.addActionListener(e -> addOrEditColumn((VBdObjectEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject(), true));
        }
        {
            jMenuItemEditType = new JMenuItem();
            jMenuItemEditType.setText("Edit");
            jMenuItemEditType.addActionListener(e -> addOrEditColumn((VBdObjectEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject(), false));
        }
        {
            jMenuItemAddToList = new JMenuItem();
            jMenuItemAddToList.setText("Add To List");
            jMenuItemAddToList.addActionListener(e -> {
                //ToDo добавить обработку добавления в список
                //jMenuItemAddToList_actionPerformed(e);
            });
        }
        jPopupMenu.add(jMenuItemAddSubType);
        jPopupMenu.add(jMenuItemEditType);
        jPopupMenu.add(jMenuItemAddToList);

        tree1.setComponentPopupMenu(jPopupMenu);

        jPopupMenuTable = new JPopupMenu();

        menuAdd = new JMenu();
        menuAdd.setText("Add");
        {
            jMenuItemAddColumn = new JMenuItem();
            jMenuItemAddColumn.setText("Add Column");
            jMenuItemAddColumn.addActionListener(this::fileTune_ActionPerformed);
            menuAdd.add(jMenuItemAddColumn);

            JMenuItem jMenuItemAddIndex = new JMenuItem();

            jMenuItemAddIndex.setText("Add Index");
            jMenuItemAddIndex.addActionListener(this::fileTune_ActionPerformed);
            menuAdd.add(jMenuItemAddIndex);


        }
        jPopupMenuTable.add(menuAdd);
        columnTable.setComponentPopupMenu(jPopupMenuTable);
        indexTable.setComponentPopupMenu(jPopupMenuTable);
    }


    public VBdObjectEntity addOrEditColumn(VBdObjectEntity object, boolean adding) {
        /*if (object != null) {
            object.setLoaded(true);
        }*/

        JAddDialog dialog = null;
        if (object.getTypeObject() != null && object.getTypeObject().equals(ObjectTypes.getDATE())) {
            new MessageWin("Это же какую новую дату хотите добавить?");
        } else {
            if (adding) {
                if (object instanceof VBdColumnsEntity) {
                    if (object.getParent() == null ||
                            object.getParent().getTypeObject() == null ||
                            !object.getParent().getTypeObject().equals(ObjectTypes.getTABLE()) ||
                            object.getParent().equals(RootObjects.getTABLE())
                            ) {
                        new MessageWin("Не выбран тип для добавления колонки или тип не является таблицей.");
                    } else {
                        dialog = new NewOrEditColumn(null, object.getParent());
                    }
                } else if (object.getCode().equals("TABLE") || object.getTypeObject().equals(ObjectTypes.getTABLE())) {
                    dialog = new NewOrEditTable(null, object);
                } else if (object.getCode().equals("NUMBER") || object.getTypeObject().equals(ObjectTypes.getNUMBER())) {
                    dialog = new NewOrEditNumber(null);
                } else if (object.getCode().equals("STRING") || object.getTypeObject().equals(ObjectTypes.getSTRING())) {
                    dialog = new NewOrEditString(null);
                } else if (object.getCode().equals("ARRAY") || object.getCode().equals("REFERENCE") ||
                        object.getTypeObject().equals(ObjectTypes.getREFERENCE())
                        || object.getTypeObject().equals(ObjectTypes.getARRAY())) {
                    dialog = new NewOrEditRefArr(null, object.getTypeObject().equals(ObjectTypes.getREFERENCE()));
                }
            } else {
                if (object.getTypeObject().equals(ObjectTypes.getTABLE())) {
                    dialog = new NewOrEditTable(object, object.getParent());
                } else if (object.getTypeObject().equals(ObjectTypes.getNUMBER())) {
                    dialog = new NewOrEditNumber(object);
                } else if (object.getTypeObject().equals(ObjectTypes.getSTRING())) {
                    dialog = new NewOrEditString(object);
                } else if (object.getTypeObject().equals(ObjectTypes.getREFERENCE())
                        || object.getTypeObject().equals(ObjectTypes.getARRAY())) {
                    dialog = new NewOrEditRefArr(object, object.getTypeObject().equals(ObjectTypes.getREFERENCE()));
                }
            }
            if (dialog != null) {
                dialog.pack();
                dialog.setVisible(true);
                if ((dialog.getAddedObj() instanceof VBdTableEntity)) {
                    tree1.addToTree(dialog.getAddedObj());
                    tree1.refresh(true);
                } else {
                    JDBTableIndexsModel indexTableModel = ((JDBTableIndexsModel) indexTable.getModel());
                    indexTableModel.loadTableByObj((VBdTableEntity) object);
                    indexTable.updateUI();

                    JDBTableColumnModel columnTableModel = ((JDBTableColumnModel) columnTable.getModel());
                    columnTableModel.loadTableByObj((VBdTableEntity) object);
                    columnTable.updateUI();
                }
                return dialog.getAddedObj();
            }
        }

        return null;
    }

    private void createUIComponents() {
        columnTable = new JTable(new JDBTableColumnModel());
        indexTable = new JTable(new JDBTableIndexsModel());
        tree1 = new JDBTree();// JDBTree.getInstance();

/*
        try {
            tree1.loadTree(true);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
*/

        // TODO: place custom component creation code here
    }

    private void fileTune_ActionPerformed(ActionEvent ae) {
        VBdObjectEntity object;
        try {
            object = (VBdObjectEntity) ((DefaultMutableTreeNode) tree1.getLastSelectedPathComponent()).getUserObject();
        } catch (NullPointerException e) {
            object = null;
        }

        if (object == null) {
            new MessageWin("Не выбран тип, в который нужно дабавить колонку.");
        } else if (!object.getTypeObject().getCode().equals("TABLE")) {
            new MessageWin("Для объекта типа " + object.getTypeObject().getName() + " не предусмотрено добавление столбца.\nДобавление возможно только для таблицы");
        } else {
            if (ae.getActionCommand().equals("Add Index")) {
                JAddDialog dialog = null;
                if (object.getTypeObject() != null && !object.getTypeObject().equals(ObjectTypes.getTABLE())) {
                    new MessageWin("Добавление индекса возможно только для таблицы.");
                } else {
                    dialog = new NewOrEditIndex(null, object);
                }
                if (dialog != null) {
                    dialog.pack();
                    dialog.setVisible(true);
                }

            } else {
                //Todo  тут идет лишний запрос к BDObjType, далее в ЭФ добавления эта инфа не передается, надо пооптимальнее сделать
                VBdColumnsEntity columns = new VBdColumnsEntity();
                columns.setJavaClass(VBdColumnsEntity.class.toString());
                columns.setParent(object);
                addOrEditColumn(columns, true);
            }
        }
    }
}
