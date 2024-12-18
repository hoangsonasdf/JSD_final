package gamecomponent.tank;

import gamecomponent.Direction;
import gamecomponent.HomeBase;
import gamecomponent.Position;
import gamecomponent.enviroment.Enviroment;
import gamecomponent.powerup.PowerUp;

import javax.swing.*;
import java.awt.*;



public class PlayerTank extends Tank {
    private int life;
    private int tier;
    private boolean shield;
    private boolean upgradedBullet;
    private PlayerTankEnum playerTankEnum;


    public PlayerTank(Position position, PlayerTankEnum playerTankEnum) {
        super(position);
        this.playerTankEnum = playerTankEnum;
        this.life = 4;
        this.direction = Direction.U;
        this.point = 0;
        this.health = 1;
        this.movementSpeed = 5;
        this.tier = 1;
        this.shield = false;
        loadImages();
        updateTierState();
        this.setSize(getImageSize());
    }



    @Override
    public void loadImages() {
        if (playerTankEnum == PlayerTankEnum.PLAYER1) {
            this.images.put(Direction.U, new ImageIcon("images/HtankU.gif").getImage());
            this.images.put(Direction.D, new ImageIcon("images/HtankD.gif").getImage());
            this.images.put(Direction.L, new ImageIcon("images/HtankL.gif").getImage());
            this.images.put(Direction.R, new ImageIcon("images/HtankR.gif").getImage());
        }
        else {
            this.images.put(Direction.U, new ImageIcon("images/HtankU2.gif").getImage());
            this.images.put(Direction.D, new ImageIcon("images/HtankD2.gif").getImage());
            this.images.put(Direction.L, new ImageIcon("images/HtankL2.gif").getImage());
            this.images.put(Direction.R, new ImageIcon("images/HtankR2.gif").getImage());
        }
    }

    @Override
    public void checkBounds() {
        int frameWidth = getParent().getWidth();
        int frameHeight = getParent().getHeight();
        int tankWidth = getImageSize().width;
        int tankHeight = getImageSize().height;

        if (position.getX() < 0) {
            position.setX(0);
        } else if (position.getX() + tankWidth > frameWidth) {
            position.setX(frameWidth - tankWidth);
        }
        if (position.getY() < 0) {
            position.setY(0);
        } else if (position.getY() + tankHeight > frameHeight) {
            position.setY(frameHeight - tankHeight);
        }
    }

    @Override
    public void handleCollision(Position oldPosition) {
        Component[] collidedComponents = checkCollisions();
        boolean shouldRevertPosition = false;

        for (Component component : collidedComponents) {
            if (component instanceof Enviroment) {
                Enviroment environment = (Enviroment) component;
                if (!environment.isCanPass()) {
                    shouldRevertPosition = true;
                    break;
                }
            }
            else if (component instanceof HomeBase || component instanceof Tank) {
                shouldRevertPosition = true;
                break;
            }
            else if (component instanceof PowerUp) {
                PowerUp powerUp = (PowerUp) component;
                powerUp.active(this);
                this.repaint();
                this.getParent().remove(powerUp);
            }
        }

        if (shouldRevertPosition) {
            position.setX(oldPosition.getX());
            position.setY(oldPosition.getY());
        }

        updatePanelPosition();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Image tankImage = this.images.get(this.direction);
        if (tankImage != null) {
            g2d.drawImage(tankImage, 0, 0, this.getWidth(), this.getHeight(), null);
        }

        if (this.shield) {
            drawShield(g2d);
        }
    }

    private void drawShield(Graphics2D g2d) {
        Stroke oldStroke = g2d.getStroke();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2.0f));

        int shieldRadius = Math.max(getWidth(), getHeight()) / 2 + 5;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        g2d.drawOval(
                centerX - shieldRadius,
                centerY - shieldRadius,
                shieldRadius * 2,
                shieldRadius * 2
        );

        g2d.setStroke(oldStroke);
    }

    public void updateTierState() {
        if (this.tier == 1) {
            this.bulletSpeed = 10;
            this.numberOfBulletPerShoot = 1;
            this.upgradedBullet = false;
        } else if (this.tier == 2) {
            this.bulletSpeed = 12;
            this.numberOfBulletPerShoot = 1;
            this.upgradedBullet = false;
        } else if (this.tier == 3) {
            this.bulletSpeed = 12;
            this.numberOfBulletPerShoot = 2;
            this.upgradedBullet = false;
        } else {
            this.bulletSpeed = 12;
            this.numberOfBulletPerShoot = 2;
            this.upgradedBullet = true;
        }
    }

    public void tierUp() {
        if (this.tier < 4) {
            this.tier++;
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public boolean isShield() {
        return shield;
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean isUpgradedBullet() {
        return upgradedBullet;
    }

    public void setUpgradedBullet(boolean upgradedBullet) {
        this.upgradedBullet = upgradedBullet;
    }

    public PlayerTankEnum getPlayerTankEnum() {
        return playerTankEnum;
    }

    public void setPlayerTankEnum(PlayerTankEnum playerTankEnum) {
        this.playerTankEnum = playerTankEnum;
    }
}
