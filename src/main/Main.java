package main;

import main.model.image.ImageModel;
import main.model.image_collection.ImageCollectionModel;
import main.model.toolbar.RateFilterModel;
import main.model.toolbar.ToolbarModel;
import main.model.toolbar.ViewSelectorModel;
import main.view.image_collection.ImageCollectionView;
import main.view.toolbar.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Created by Dongwoo on 04/03/2016.
 */
public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI(){
        JFrame frame = new JFrame("Fotag!");

        Container pane = frame.getContentPane();
        pane.setPreferredSize(new Dimension(1243,768));

        // Create Models
        ImageCollectionModel imageCollectionModel = new ImageCollectionModel();
        ToolbarModel toolbarModel = new ToolbarModel();
        RateFilterModel rateFilterModel = new RateFilterModel();
        ViewSelectorModel viewSelectorModel = new ViewSelectorModel();

        // Create Toolbar
        Toolbar toolbar = new Toolbar(toolbarModel, imageCollectionModel, rateFilterModel, viewSelectorModel);
        toolbarModel.addObserver(toolbar);
        pane.add(toolbar, BorderLayout.NORTH);

        // Create Scroll Panel - Image Collection View
        ImageCollectionView imageCollectionView= new ImageCollectionView(imageCollectionModel, rateFilterModel, viewSelectorModel);
        imageCollectionModel.addObserver(imageCollectionView);
        rateFilterModel.addObserver(imageCollectionView);
        viewSelectorModel.addObserver(imageCollectionView);
        JScrollPane scrollPane = new JScrollPane(imageCollectionView,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        pane.add(scrollPane, BorderLayout.CENTER);

        // Frame config
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(625, 415));
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("SAVING:");
                try{
                    boolean ret;
                    File saveFile = new File("./saved/saved.ser");
                    if (saveFile.getParentFile().exists()){
                        System.out.println("Directory path exists");
                    } else {
                        System.out.println("Directory path does not exist, create directories");
                        ret = saveFile.getParentFile().mkdirs();
                        if (ret){
                            System.out.println("directories created successfully");
                        } else {
                            System.out.println("directory creation failed");
                        }
                    }
                    ret = saveFile.createNewFile();
                    if (ret){
                        System.out.println("created a new save file");
                    } else {
                        System.out.println("save file already exists");
                        ret = saveFile.delete();
                        if (ret){
                            System.out.println("deleted existing file successfully");
                            ret = saveFile.createNewFile();
                            if (ret){
                                System.out.println("then created new one successfully");
                            } else {
                                System.out.println("creating new one failed");
                            }
                        } else {
                            System.out.println("deleting existing file failed");
                        }
                    }
                    FileOutputStream fos = new FileOutputStream(saveFile);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(imageCollectionModel);
                    oos.close();
                    fos.close();
                } catch (FileNotFoundException err){
                    err.printStackTrace();
                } catch (IOException err){
                    err.printStackTrace();
                }
                System.out.println("Closed");
                e.getWindow().dispose();
            }

            @Override
            public void windowOpened(WindowEvent e) {
                System.out.println("Launched");
                try {
                    File savedFile = new File("./saved/saved.ser");
                    if (savedFile.exists()){
                        FileInputStream fis = new FileInputStream(savedFile);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        imageCollectionModel.loadModel((ImageCollectionModel) ois.readObject());
                        ois.close();
                        fis.close();
                    }
                } catch (FileNotFoundException err){
                    err.printStackTrace();
                } catch (IOException err){
                    err.printStackTrace();
                } catch (ClassNotFoundException err){
                    err.printStackTrace();
                }
            }
        });
    }
}
