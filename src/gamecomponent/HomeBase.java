package gamecomponent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeBase extends JPanel {
    private Position position;
    private Image image;

    public HomeBase(Position position){
        this.position = position;
        this.image = new ImageIcon("images/home.jpg").getImage();
        setSize(this. getImageSize());
    }
    public Dimension getImageSize() {
        if (image != null) {
            return new Dimension(image.getWidth(null), image.getHeight(null));
        }
        return new Dimension(50, 50);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
    }
}
