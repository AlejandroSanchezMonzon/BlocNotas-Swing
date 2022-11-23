package controllers;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

public class ViewController extends JFrame {
    private Clipboard portaPapeles;
    private File archivo = null;

    public ViewController() {
    }

    public void setStatus(JTextArea areaTexto, JLabel estadoLabel) throws BadLocationException {
        int caretpos = areaTexto.getCaretPosition();
        int fila;
        int columna;

        fila = areaTexto.getLineOfOffset(caretpos);
        columna = caretpos - areaTexto.getLineStartOffset(fila);

        estadoLabel.setText("Línea: " + fila + ", Columna: " + columna);
    }


    public void createFile(JTextArea areaTexto) {
        var option = JOptionPane.showConfirmDialog(null, "¿Guardar antes de crear un nuevo archivo?", "Nuevo archivo", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);

        String text = areaTexto.getText();

        if (option == JOptionPane.CANCEL_OPTION) {
            areaTexto.setText("");
        } else if (option == JOptionPane.OK_OPTION) {
            JFileChooser fileChooser = new JFileChooser();
            int selection = fileChooser.showSaveDialog(areaTexto);
            File file = fileChooser.getSelectedFile();

            try {
                try (FileWriter writer = new FileWriter((file))) {
                    if (selection == JFileChooser.APPROVE_OPTION) {
                        writer.write(text);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openFile(JTextArea areaTexto) {
        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showOpenDialog(areaTexto);
        File file = fileChooser.getSelectedFile();
        this.archivo = file;

        try {
            if (selection == JFileChooser.APPROVE_OPTION) {
                Scanner sc = new Scanner(file);
                while (sc.hasNext()) {
                    areaTexto.insert(sc.nextLine() + "\n", areaTexto.getText().length());
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void save(JTextArea areaTexto) {
        String text = areaTexto.getText();
        if (this.archivo == null) {
            JFileChooser fileChooser = new JFileChooser();
            int selection = fileChooser.showSaveDialog(areaTexto);
            if (selection == JFileChooser.APPROVE_OPTION) {
                this.archivo = fileChooser.getSelectedFile();
            }
        }

        try (FileWriter writer = new FileWriter((this.archivo))) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAs(JTextArea areaTexto) {
        String text = areaTexto.getText();

        JFileChooser fileChooser = new JFileChooser();
        int selection = fileChooser.showSaveDialog(areaTexto);
        File file = fileChooser.getSelectedFile();

        try {
            try (FileWriter writer = new FileWriter((file))) {
                if (selection == JFileChooser.APPROVE_OPTION) {
                    writer.write(text);
                    areaTexto.setText(text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void imprimir(JTextArea areaTexto) {
        try {
            areaTexto.print();
        } catch (PrinterException e) {
            e.printStackTrace();
        }
    }

    public void deshacer(UndoManager manager) {
        manager = new UndoManager();
        manager.setLimit(10);

        if (manager.canUndo()) {
            manager.undo();
        }
    }

    public void rehacer(UndoManager manager) {
        manager = new UndoManager();
        manager.setLimit(10);

        if (manager.canRedo()) {
            manager.redo();
        }
    }

    public void copiar(JTextArea areaTexto) {
        portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();

        if (areaTexto.getSelectedText() != null) {
            StringSelection selection = new StringSelection("" + areaTexto.getSelectedText());
            portaPapeles.setContents(selection, selection);
        }
    }

    public void cortar(JTextArea areaTexto) {
        portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();

        if (areaTexto.getSelectedText() != null) {
            StringSelection selection = new StringSelection("" + areaTexto.getSelectedText());
            portaPapeles.setContents(selection, selection);
            areaTexto.getSelectedText();
        }

        areaTexto.getSelectedText();
        areaTexto.cut();
    }

    public void pegar(JTextArea areaTexto) {
        portaPapeles = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable datos = portaPapeles.getContents(null);
        try {
            if (datos != null && datos.isDataFlavorSupported(DataFlavor.stringFlavor))
                areaTexto.replaceSelection("" + datos.getTransferData(DataFlavor.stringFlavor));
        } catch (UnsupportedFlavorException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(JTextArea areaTexto) {
        String aux = areaTexto.getText().substring(0, areaTexto.getText().length() - 1);
        areaTexto.setText(aux);
    }

    public void color(JTextArea areaTexto) {
        var color = JColorChooser.showDialog(null, "Seleccione un color:", Color.WHITE);

        areaTexto.setBackground(color);
    }

    public void showStatus(JLabel estadoLabel) {
        estadoLabel.setVisible(!estadoLabel.isShowing());
    }

    public void showAbout() {
        JOptionPane.showMessageDialog(null, "Autor: Alejandro Sánchez Monzón", "Acerca de...", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showHelp() {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        String uri = "https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html";
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(URI.create(uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}