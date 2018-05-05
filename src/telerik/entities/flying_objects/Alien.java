package telerik.entities.flying_objects;

import telerik.Constants;
import telerik.Position;
import telerik.Size;
import telerik.game_states.GameStateType;
import telerik.game_states.PlayState;
import telerik.interfaces.*;

public class Alien extends FlyingObject implements ReachingPlanet, Movable, CollidesWithOwnShip, CollidesWithOwnBullet {
    private int speed;

    public Alien(PlayState game, int x, int speed) {
        super(game);

        this.speed = speed;

        this.setSize(new Size(Constants.ALIEN_WIDTH, Constants.ALIEN_HIGHT));
        this.setPosition(new Position(x, Constants.CONTROL_PANEL_HEIGHT));
        this.setImage(game.getSpriteSheet().getImage(600, 0, Constants.ALIEN_WIDTH, Constants.ALIEN_HIGHT));
        this.setBounds();

        addToMovableCollection();
        addToCollidableWithOwnBullet();
        addToCollidableWithOwnShip();
    }

    @Override
    public void update() {
        getPosition().setY(getPosition().getY() + speed);
        getBounds().moveBounds(this);

        if (getPosition().getY() > Constants.HEIGHT) {
            onPlanetReach();
        }
    }

    @Override
    public void addToMovableCollection() {
        getGame().getHandler().addMovable(this);
    }

    @Override
    public void onCollide() {
        getGame().getHandler().addToRemove(this);
    }

    @Override
    public void onPlanetReach() {
        int stateIndex = GameStateType.GAMEOVER.ordinal();
        getGame().getGameStateManager().setState(stateIndex);
    }

    @Override
    public void addToCollidableWithOwnBullet() {
        getGame().getHandler().addCollidableWithBullet(this);
    }

    @Override
    public void addToCollidableWithOwnShip() {
        getGame().getHandler().addCollidableWithShip(this);
    }

    @Override
    public void onCollideWithShip() {
        onCollide();
    }

    @Override
    public void onCollideWithBullet(FriendlyBullet bullet) {
        onCollide();
        getGame().getPlayer().setPoints(getGame().getPlayer().getPoints() + bullet.getPower());
    }


}
