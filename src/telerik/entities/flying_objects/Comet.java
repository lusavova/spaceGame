package telerik.entities.flying_objects;

import telerik.Constants;
import telerik.system.Position;
import telerik.system.Size;
import telerik.entities.Explosion;
import telerik.enumerators.CometType;
import telerik.game_states.PlayState;
import telerik.interfaces.CollidesWithOwnShip;
import telerik.abstract_classes.FlyingObject;
import telerik.interfaces.HurtingShip;
import telerik.interfaces.Updatable;

public class Comet extends FlyingObject implements Updatable, CollidesWithOwnShip, HurtingShip {

    private CometType kind;
    private int speed;
    private int power;
    private int width;
    private int height;

    public Comet(PlayState game, CometType kind, int y, int speed) {
        super(game);

        this.kind = kind;
        this.speed = speed;

        this.power = Constants.COMET_POWER;
        this.width = Constants.COMET_WIDTH;
        this.height = Constants.COMET_HIGHT;

        setSize(new Size(width, height));

        if (kind == CometType.LEFT) {
            this.setPosition(new Position(0 - width, y));
        } else if (kind == CometType.RIGHT) {
            this.setPosition(new Position(Constants.WIDTH, y));
        }

        setSprites(kind);
        setBounds();
    }

    public void setSprites(CometType kind) {
        int y;

        if (kind == CometType.LEFT) {
            y = 270;
        } else {
            y = 214;
        }

        for (int i = 0; i < 6; i++) {
            int x = width * i;
            setImage(getGame().getSpriteSheet().getImage(x, y, width, height));
        }
    }

    @Override
    public void update() {
        updateFrame();

        getPosition().setY(getPosition().getY() + speed);

        if (getPosition().getY() >= Constants.HEIGHT) {
            resetCometPosition();
        }

        if (kind == CometType.LEFT) {
            getPosition().setX(getPosition().getX() + speed);
        }

        if (this.kind == CometType.RIGHT) {
            getPosition().setX(getPosition().getX() - speed);
        }

        getBounds().moveBounds(this);
    }

    private void resetCometPosition() {
        speed = getGame().getSpawner().getRnd().nextInt(3) + 2;
        getPosition().setY(getGame().getSpawner().getRnd().nextInt(Constants.HEIGHT - 250));

        if (kind == CometType.LEFT) {
            getPosition().setX(0 - width);
        }
        if (this.kind == CometType.RIGHT) {
            getPosition().setX(Constants.WIDTH);
        }
    }


    @Override
    public void onCollide() {
        resetCometPosition();
    }

    private void updateFrame() {
        frame++;

        if (frame == getImageList().size()) {
            frame = 0;
        }
    }

    @Override
    public void onCollideWithShip() {
        new Explosion(getGame(), this);

        onCollide();
        int currentHealth = getGame().getPlayer().getShip().getHealth();
        getGame().getPlayer().getShip().setHealth(currentHealth - power);

        System.out.println(this + " hit you. -" + power + " Health.");
    }

    @Override
    public String toString(){
        return "Comet";
    }

    public CometType getKind() {
        return kind;
    }
}
