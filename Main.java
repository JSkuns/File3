import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    // лист с объектами сохранений
    private static final ArrayList<GameProgress> saveList = new ArrayList<>();
    // лист с именами файлов
    private static final ArrayList<String> nameList = new ArrayList<>();
    // основной путь
    private static final File mainDir = new File("d:\\1\\Games\\savegames\\");


    public static void main(String[] args) {

        // 1. Создать три экземпляра класса GameProgress
        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(50, 17, 7, 2210.11);
        GameProgress gameProgress3 = new GameProgress(63, 51, 23, 57320.59);

        // 2. Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи
        saveGame(gameProgress1, mainDir);
        saveGame(gameProgress2, mainDir);
        saveGame(gameProgress3, mainDir);

        // 3. Созданные файлы сохранений из папки savegames запаковать в архив zip.
        zipFiles(nameList, mainDir);

        // 4. Удалить файлы сохранений, лежащие вне архива
        removeFiles(nameList);

    }


    private static void removeFiles(ArrayList<String> nameList) {
        for (String s : nameList) {
            File f = new File(s);
            if (f.delete()) System.out.println("Файл " + s + " удален");
        }
    }


    private static void zipFiles(ArrayList<String> nameList, File dir) {
        try (ZipOutputStream zOut = new ZipOutputStream(new FileOutputStream(dir + "\\zipFile.zip"))) {
            for (int i = 0; i < nameList.size(); i++) {
                try (FileInputStream fis = new FileInputStream(nameList.get(i))) {
                    ZipEntry entry = new ZipEntry("zip" + (i + 1) + ".dat");
                    zOut.putNextEntry(entry);
                    // считываем содержимое файла в массив byte
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    // добавляем содержимое к архиву
                    zOut.write(buffer);
                    // закрываем текущую запись для новой записи
                    zOut.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveGame(GameProgress saveGame, File dir) {
        saveList.add(saveGame);
        try (FileOutputStream fos = new FileOutputStream(dir + "\\save" + (saveList.indexOf(saveGame) + 1) + ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(saveGame);
            nameList.add(dir + "\\save" + (saveList.indexOf(saveGame) + 1) + ".dat");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
