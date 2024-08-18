package com.tabajara.apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import com.tabajara.negocio.Anotacao;

class AnotacaoItem extends JPanel implements ListCellRenderer<Anotacao> {
    private static final int PHOTO_SIZE = 80;

    public AnotacaoItem() {
        setLayout(new BorderLayout());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Anotacao> list, Anotacao task, int index, boolean isSelected, boolean cellHasFocus) {
        removeAll();
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(task.getTitulo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);

        JLabel descriptionLabel = new JLabel("<html>" + task.getDescricao().replaceAll("\n", "<br/>") + "</html>");
        descriptionLabel.setForeground(Color.BLACK);

        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);

        JLabel photoLabel = new JLabel();
        try {
            ImageIcon photoIcon = new ImageIcon(convertByteArrayToImage(task.getFoto()));
            photoLabel.setIcon(photoIcon);
        } catch (IOException e) {
            MensagemErro.showException(e);
            photoLabel.setText("No Image");
        }
        
        photoLabel.setPreferredSize(new Dimension(PHOTO_SIZE, PHOTO_SIZE));
        photoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        setBackground(Color.decode(task.getCor()));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(textPanel, BorderLayout.CENTER);
        add(photoLabel, BorderLayout.EAST);

        if (isSelected) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        } else {
            setBorder(BorderFactory.createEmptyBorder());
        }

        return this;
    }

    private Image convertByteArrayToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage bufferedImage = ImageIO.read(bais);
        return bufferedImage;
    }
}