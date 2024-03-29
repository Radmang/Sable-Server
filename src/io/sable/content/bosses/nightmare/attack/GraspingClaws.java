package io.sable.content.bosses.nightmare.attack;

import java.util.List;

import com.google.common.collect.Lists;
import io.sable.Server;
import io.sable.content.bosses.nightmare.Nightmare;
import io.sable.content.bosses.nightmare.NightmareAttack;
import io.sable.content.combat.Hitmark;
import io.sable.model.Animation;
import io.sable.model.StillGraphic;
import io.sable.model.cycleevent.CycleEvent;
import io.sable.model.cycleevent.CycleEventContainer;
import io.sable.model.cycleevent.CycleEventHandler;
import io.sable.model.entity.npc.NPCClipping;
import io.sable.model.entity.player.Position;
import io.sable.util.Misc;

public class GraspingClaws extends NightmareAttack {

    private static final Position LOW = new Position(3863, 9942, 3);
    private static final Position HIGH = new Position(3881, 9960, 3);
    private static final Position DELTA = LOW.delta(HIGH);
    private static final int PORTAL_COUNT = 48;
    private static final int PORTAL_GFX = 1767;

    public static Position random() {
        return new Position(LOW.getX() + Misc.trueRand(DELTA.getX()), LOW.getY() + Misc.trueRand(DELTA.getY()), 3);
    }

    @Override
    public void tick(Nightmare nightmare) {
        if (getTicks() == 0) {
            nightmare.startAnimation(new Animation(8598));
            nightmare.requestTransform(9426);

            // Nightmare's portal
            new Portal(-1, nightmare.getPosition(), 3).start(nightmare);

            // Other portals
            for (Position position : getPortalPositions(nightmare)) {
                Portal portal = new Portal(PORTAL_GFX, position, 1);
                Server.playerHandler.sendStillGfx(portal, nightmare.getInstance());
                portal.start(nightmare);
            }
        }

        if (getTicks() == 6) {
            nightmare.transformToStandard();
            stop();
        }
    }

    private List<Position> getPortalPositions(Nightmare nightmare) {
        List<Position> portals = Lists.newArrayList();

        nightmare.getInstance().getPlayers().forEach(player -> {
            if (player.freezeTimer == 0 && Misc.random(2) == 0) {
                portals.add(player.getPosition());
            }
        });

        for (int count = 0; count < PORTAL_COUNT; count++) {
            Position position;

            main: while (true) {
                position = nightmare.getInstance().resolve(random());
                final Position position1 = position;

                if (portals.contains(position) || nightmare.insideOf(position))
                    continue;
                if (nightmare.getTotems().getTotems().stream().anyMatch(t -> t.insideOf(position1)))
                    continue;
                if (portals.stream().map(position1::deltaAbsolute).anyMatch(p -> p.getX() == 1 && p.getY() == 0
                        || p.getY() == 1 && p.getX() == 0
                        || p.getX() == 1 && p.getY() == 1)) {
                    continue;
                }

                if (nightmare.getInstance().getPlayers().stream().anyMatch(plr -> plr.getPosition().equals(position1) && plr.freezeTimer > 0))
                    continue;

                break;
            }

            portals.add(position);
        }

        return portals;
    }

    private static class Portal extends StillGraphic {
        private final int size;

        Portal(int id, Position position, int size) {
            super(id, position);
            this.size = size;
        }

        void start(Nightmare nightmare) {
            CycleEventHandler.getSingleton().addEvent(nightmare, new CycleEvent() {
                @Override
                public void execute(CycleEventContainer container) {
                    nightmare.getInstance().getPlayers().forEach(player -> {
                        if (NPCClipping.withinBlock(getPosition().getX(), getPosition().getY(), size, player.getX(), player.getY())) {
                            player.appendDamage(nightmare, 35 + Misc.trueRand(15), Hitmark.HIT);
                        }
                    });
                    container.stop();
                }
            }, 5);
        }
    }

}
