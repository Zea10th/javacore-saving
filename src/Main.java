import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        final String SAVEGAME_DIR = "/Users/zea10th/GameDistr/savegames";
        List<GameProgress> savegames = new ArrayList<>();

        savegames.add(new GameProgress(100, 5, 2, 850.0));
        savegames.add(new GameProgress(90, 6, 3, 1500.0));
        savegames.add(new GameProgress(25, 10, 5, 3000.1));

        for (GameProgress progress : savegames) {
            savegame(SAVEGAME_DIR, progress);
        }

        makeZip(SAVEGAME_DIR);
        deleteSaves(SAVEGAME_DIR);
    }

    private static void deleteSaves(String dir) {
        for (File file : new File(dir).listFiles()) {
            if (file.getName().startsWith("SaveGame")) {
                file.delete();
            }
        }
    }

    private static void makeZip(String dir) {
        try {
            FileOutputStream zipFile = new FileOutputStream(dir + "/archive.zip");
            ZipOutputStream zipOut = new ZipOutputStream(zipFile);
            FileInputStream fis;
            byte[] buffer;

            for (File file : new File(dir).listFiles()) {
                fis = new FileInputStream(file);
                buffer = new byte[fis.available()];
                zipOut.putNextEntry(new ZipEntry(file.getName()));
                fis.read(buffer);
                zipOut.write(buffer);
                zipOut.closeEntry();
                fis.close();
            }
            zipOut.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void savegame(String dir, GameProgress progress) {
        File save = new File(dir, "SaveGame" + progress.getExampleNum());
        try (FileOutputStream fos = new FileOutputStream(save);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
