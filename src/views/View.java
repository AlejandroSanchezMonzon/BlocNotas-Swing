package views;

import controllers.ViewController;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;

public class View extends JFrame {
    ViewController controller;
    UndoManager manager;
    ScrollPane scrollPane;
    JPanel panelPrincipal;
    JMenuBar barraMenu;
    JMenu archivoMenu;
    JMenu edicionMenu;
    JMenu formatoMenu;
    JMenu verMenu;
    JMenu ayudaMenu;
    JMenuItem nuevoItem;
    JMenuItem abrirItem;
    JMenuItem guardarItem;
    JMenuItem guardarComoItem;
    JMenuItem imprimirItem;
    JMenuItem deshacerItem;
    JMenuItem rehacerItem;
    JMenuItem copiarItem;
    JMenuItem cortarItem;
    JMenuItem pegarItem;
    JMenuItem eliminarItem;
    JMenuItem colorFondoItem;
    JMenuItem barraEstadoItem;
    JMenuItem acercaDeItem;
    JMenuItem verAyudaItem;
    JTextArea areaTexto;
    JLabel estadoLabel;


    public View() throws HeadlessException {
        controller = new ViewController();
        manager = new UndoManager();
        initComponents();
    }

    private void initComponents() {
        panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.BLACK);

        panelPrincipal.setLayout(new BorderLayout());

        scrollPane = new ScrollPane();

        areaTexto = new JTextArea();

        barraMenu = new JMenuBar();

        estadoLabel = new JLabel("Estado.");
        estadoLabel.setForeground(Color.WHITE);
        estadoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        archivoMenu = new JMenu("Archivo");
        edicionMenu = new JMenu("Edicion");
        formatoMenu = new JMenu("Formato");
        verMenu = new JMenu("Ver");
        ayudaMenu = new JMenu("Ayuda");

        nuevoItem = new JMenuItem("Nuevo");

        abrirItem = new JMenuItem("Abrir");
        guardarItem = new JMenuItem("Guardar");
        guardarComoItem = new JMenuItem("Guardar como...");
        imprimirItem = new JMenuItem("Imprimir");

        manager = new UndoManager();
        deshacerItem = new JMenuItem("Deshacer");
        rehacerItem = new JMenuItem("Rehacer");
        copiarItem = new JMenuItem("Copiar");
        cortarItem = new JMenuItem("Cortar");
        pegarItem = new JMenuItem("Pegar");
        eliminarItem = new JMenuItem("Eliminar");

        colorFondoItem = new JMenuItem("Color de fondo");

        barraEstadoItem = new JMenuItem("Barra de Estado");

        acercaDeItem = new JMenuItem("Acerca de");
        verAyudaItem = new JMenuItem("Ver ayuda");

        barraMenu.add(archivoMenu);
        archivoMenu.add(nuevoItem);
        archivoMenu.add(abrirItem);
        archivoMenu.add(guardarItem);
        archivoMenu.add(guardarComoItem);
        archivoMenu.add(imprimirItem);

        barraMenu.add(edicionMenu);
        edicionMenu.add(deshacerItem);
        edicionMenu.add(rehacerItem);
        edicionMenu.add(copiarItem);
        edicionMenu.add(cortarItem);
        edicionMenu.add(pegarItem);
        edicionMenu.add(eliminarItem);

        barraMenu.add(formatoMenu);
        formatoMenu.add(colorFondoItem);

        barraMenu.add(verMenu);
        verMenu.add(barraEstadoItem);

        barraMenu.add(ayudaMenu);
        ayudaMenu.add(acercaDeItem);
        ayudaMenu.add(verAyudaItem);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        scrollPane.add(areaTexto);
        panelPrincipal.add(estadoLabel, BorderLayout.PAGE_END);

        areaTexto.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                try {
                    controller.setStatus(areaTexto, estadoLabel);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        nuevoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.createFile(areaTexto);
            }
        });

        abrirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openFile(areaTexto);
            }
        });

        guardarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.save(areaTexto);
            }
        });

        guardarComoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveAs(areaTexto);
            }
        });

        imprimirItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.imprimir(areaTexto);
            }
        });

        areaTexto.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                manager.addEdit(e.getEdit());
            }
        });

        deshacerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.deshacer(manager);
            }
        });

        rehacerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.rehacer(manager);
            }
        });

        copiarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.copiar(areaTexto);
            }
        });

        cortarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.cortar(areaTexto);
            }
        });

        pegarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.pegar(areaTexto);
            }
        });

        eliminarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.delete(areaTexto);
            }
        });

        colorFondoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.color(areaTexto);
            }
        });

        barraEstadoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showStatus(estadoLabel);
            }
        });

        acercaDeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showAbout();
            }
        });

        verAyudaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showHelp();
            }
        });

        this.setJMenuBar(barraMenu);
        this.add(panelPrincipal);

        this.pack();
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(1080, 720));
    }
}
