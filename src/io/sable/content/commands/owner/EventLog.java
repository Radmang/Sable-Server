package io.sable.content.commands.owner;

import io.sable.Server;
import io.sable.content.commands.Command;
import io.sable.model.cycleevent.CycleEventContainer;
import io.sable.model.cycleevent.CycleEventHandler;
import io.sable.model.entity.player.Player;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class EventLog extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        player.sendMessage("Writing event log..");
        Server.getIoExecutorService().submit(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./logs/event-log.txt"))) {
                List<CycleEventContainer> events = CycleEventHandler.getSingleton().getEvents();
                writer.write("Events: " + events.size());
                writer.newLine();

                for (CycleEventContainer event : events) {
                    writer.write(event.toString());
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
