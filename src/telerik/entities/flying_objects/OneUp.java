package telerik.entities.flying_objects;


import telerik.Constants;
import telerik.system.Position;
import telerik.system.Size;
import telerik.game_states.PlayState;
import telerik.interfaces.Collectable;
import telerik.interfaces.CollidesWithOwnShip;
import telerik.abstract_classes.Entity;

public class OneUp extends Entity implements CollidesWithOwnShip, Collectable {

    private int live;

    public OneUp(PlayState game, int x, int y) {
        super(game);

        this.live = Constants.ONE_UP_lIVE;
        int width = Constants.ONE_UP_WIDTH;
        int height = Constants.ONE_UP_HEIGHT;

        this.setSize(new Size(width, height));
        this.setPosition(new Position(x, y));
        this.setImage(game.getSpriteSheet().getImage(542, 0, width, height));

        this.setBounds();
    }

    @Override
    public void onCollide() {
        getGame().getHandler().addToRemove(this);
    }


    @Override
    public void onCollideWithShip() {
        onCollide();
        int currentLives = getGame().getPlayer().getLives();
        getGame().getPlayer().setLives(currentLives + 1);

        System.out.println(this + "! +1 Live");
    }

    @Override
    public void shouldDie() {
        live--;
        if(live == 0) {
            onCollide();
        }
    }

    @Override
    public String toString(){
        return "One up";
    }
}
