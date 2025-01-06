package pl.skidam.automodpack_loader_core;

import pl.skidam.automodpack_core.callbacks.Callback;
import pl.skidam.automodpack_loader_core.client.Changelogs;
import pl.skidam.automodpack_loader_core.loader.LoaderManager;
import pl.skidam.automodpack_core.loader.LoaderManagerService;
import pl.skidam.automodpack_loader_core.screen.ScreenManager;
import pl.skidam.automodpack_loader_core.utils.UpdateType;

import java.awt.*;
import java.nio.file.Path;

import static pl.skidam.automodpack_core.GlobalVariables.*;

public class ReLauncher {

    // TODO clean up this class
    private static final String updateMessage = "Successfully updated AutoModpack!";

    private final Path modpackDir;
    private final UpdateType updateType;
    private final Changelogs changelogs;

    public ReLauncher() {
        modpackDir = null;
        updateType = null;
        changelogs = null;
    }

    public ReLauncher(UpdateType updateType) {
        this.modpackDir = null;
        this.updateType = updateType;
        this.changelogs = null;
    }

    public ReLauncher(Path modpackDir, UpdateType updateType) {
        this.modpackDir = modpackDir;
        this.updateType = updateType;
        this.changelogs = null;
    }

    public ReLauncher(Path modpackDir, UpdateType updateType, Changelogs changelogs) {
        this.modpackDir = modpackDir;
        this.updateType = updateType;
        this.changelogs = changelogs;
    }

    public void restart(boolean restartInPreload, Callback... callbacks) {
        if (preload && !restartInPreload) return;

        boolean isClient = LOADER_MANAGER.getEnvironmentType() == LoaderManagerService.EnvironmentType.CLIENT;
        boolean isHeadless = GraphicsEnvironment.isHeadless();

        if (isClient) {
            if (updateType != null && new ScreenManager().getScreenString().isPresent() && !new ScreenManager().getScreenString().get().toLowerCase().contains("restartscreen")) {
                new ScreenManager().restart(modpackDir, updateType, changelogs);
                return;
            }

            if (preload) {
                if (isHeadless) {
                    LOGGER.info("Please restart the game to apply updates!");
                } else {
                    new Windows().restartWindow(updateMessage, callbacks);
                }
            }
        } else {
            LOGGER.info("Please restart the server to apply updates!");
        }

        for (Callback callback : callbacks) {
            try {
                callback.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }
}
