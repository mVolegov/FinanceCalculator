package Models;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Balance {
    private List<Asset> assetsList = new ArrayList<>();
    private List<Liability> liabilitiesList = new ArrayList<>();

//    {
//        assetsList.add(new Asset("Деньги", 12000));
//        assetsList.add(new Asset("ОС", 1000));
//        assetsList.add(new Asset("ДЗ", 4050));
//
//        liabilitiesList.add(new Liability("Уставный капитал", 10));
//        liabilitiesList.add(new Liability("НП", 17    000));
//        liabilitiesList.add(new Liability("Резервы", 40));
//    }

    public Balance() {
        // серелиазовать какие-то данные
//        saveDataToFiles();

        // десереализовывать данные из файла в листы
        loadDataFromFiles();
    }

    public String[][] getAssetsAsStringArray() {
        String[][] assets = new String[assetsList.size()][2];

        for (int i = 0; i < assetsList.size(); i++) {
            Asset tempAsset = assetsList.get(i);

            assets[i][0] = tempAsset.getTitle();
            assets[i][1] = String.valueOf(tempAsset.getSum());
        }

        return assets;
    }

    public String[][] getLiabilitiesAsStringArray() {
        String[][] liabilities = new String[liabilitiesList.size()][2];

        for (int i = 0; i < liabilitiesList.size(); i++) {
            Liability tempLiability = liabilitiesList.get(i);

            liabilities[i][0] = tempLiability.getTitle();
            liabilities[i][1] = String.valueOf(tempLiability.getSum());
        }

        return liabilities;
    }

    public double getAssetsSum() {
        double sum = 0;

        for (Asset asset : assetsList) {
            sum += asset.getSum();
        }

        return sum;
    }

    public double getLiabilitiesSum() {
        double sum = 0;

        for (Liability liability : liabilitiesList) {
            sum += liability.getSum();
        }

        return sum;
    }

    private void loadDataFromFiles() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("./src/Database/Assets.bin"))) {
            assetsList = (List<Asset>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("./src/Database/Liabilities.bin"))) {
            liabilitiesList = (List<Liability>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToFiles() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("./src/Database/Assets.bin"))) {
            objectOutputStream.writeObject(assetsList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("./src/Database/Liabilities.bin"))) {
            objectOutputStream.writeObject(liabilitiesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
