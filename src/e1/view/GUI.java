package e1.view;

import e1.controller.Logics;
import e1.controller.LogicsImpl;
import utils.Position;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.event.ActionListener;
import java.util.Optional;

public class GUI extends JFrame {

    @Serial
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton, Position> buttons = new HashMap<>();
    private final Logics logics;
    private final static int SIZE = 5;

    public GUI() {
        this.logics = new LogicsImpl(SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * SIZE, 100 * SIZE);

        JPanel panel = new JPanel(new GridLayout(SIZE,SIZE));
        this.getContentPane().add(BorderLayout.CENTER,panel);

        ActionListener al = (e) -> {
            final JButton bt = (JButton)e.getSource();
            final Position selectedPosition = buttons.get(bt);
            logics.hit(selectedPosition);
            if (logics.isOver()) {
            	System.exit(0);
            } else {
                draw();
            }
        };

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                final JButton jb = new JButton(" ");
                jb.addActionListener(al);
                this.buttons.put(jb, new Position(i, j));
                panel.add(jb);
            }
        }
        this.draw();
        this.setVisible(true);
    }

    private Optional<Position> findPawn() {
        return buttons.entrySet().stream()
            .filter(e -> e.getKey().getText().equals("K"))
            .map(Entry::getValue)
            .findAny();
    }

    private void draw() {
        final var pawnPositions = logics.getPawnsPosition();
        final var knightPosition = logics.getKnightPosition();
    	for (var entry: this.buttons.entrySet()) {
    		String str = pawnPositions.contains(entry.getValue()) ? "*" :
    					 knightPosition.equals(entry.getValue()) ? "K" : " ";
    		entry.getKey().setText(str);
    	}
    }

}
