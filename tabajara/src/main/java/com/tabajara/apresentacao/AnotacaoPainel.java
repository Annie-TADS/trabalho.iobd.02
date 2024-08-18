package com.tabajara.apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.tabajara.negocio.Anotacao;

public class AnotacaoPainel extends JPanel {
    private static final int PHOTO_SIZE = 80;

    public AnotacaoPainel(Anotacao anotacao) {
        setLayout(new BorderLayout());
        setBackground(Color.decode("#"+anotacao.getCor()));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(anotacao.getTitulo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);

        JLabel descriptionLabel = new JLabel("<html>" + anotacao.getDescricao() + "</html>");
        descriptionLabel.setForeground(Color.BLACK);

        textPanel.add(titleLabel);
        textPanel.add(descriptionLabel);

        JLabel photoLabel = new JLabel();
        try {
            ImageIcon photoIcon = new ImageIcon(convertByteArrayToImage(anotacao.getFoto()));
            photoLabel.setIcon(photoIcon);
        } catch (IOException e) {
            MensagemErro.showException(e);
            photoLabel.setText("No Image");
        }
        photoLabel.setPreferredSize(new Dimension(PHOTO_SIZE, PHOTO_SIZE));
        photoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        add(textPanel, BorderLayout.CENTER);
        add(photoLabel, BorderLayout.EAST);
    }

    private Image convertByteArrayToImage(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        BufferedImage bufferedImage = ImageIO.read(bais);
        return bufferedImage;
    }
}