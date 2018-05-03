package telerik.entities.flying_objects;


import telerik.Position;
import telerik.Size;
import telerik.game_states.PlayState;
import telerik.interfaces.Collectable;
import telerik.interfaces.Entity;

public class LevelUp extends Entity implements Collectable {

    public LevelUp(PlayState game) {
        super(game);
        this.setSize(new Size(33, 25));
        this.setPosition(new Position(20, 110));
        this.setImage(game.getSpriteSheet().getImage(567, 0, getSize().getWidth(), getSize().getHeight()));

    }


    @Override
    public void onCollect() {
        // TODO add one life
    }
}
