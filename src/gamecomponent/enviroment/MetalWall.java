package gamecomponent.enviroment;

import gamecomponent.Position;
import lombok.Data;

import javax.swing.*;

@Data
public class MetalWall extends Enviroment{
    public MetalWall(Position position) {
        super(position);
        this.canPass = false;
        this.canDestroy = false;
        this.image = new ImageIcon("images/metalWall.gif").getImage();
    }


}
