package kryowizardry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        var name = scanner.nextLine();
        var isEvil = Boolean.parseBoolean(scanner.nextLine());
        var filename = scanner.nextLine();
        var wizard = new Wizard(name, isEvil);

        try {
            new Solution().doSerialization(wizard, filename);
        } catch (IOException e) {
            System.out.println("Serialization failed.");
        }

        Kryo kryo = new Kryo();
        kryo.register(Wizard.class);
        try (Input kryoInput = new Input(new FileInputStream(filename))) {

            var deserialized = kryo.readObject(kryoInput, Wizard.class);

            if (Objects.equals(deserialized.getName(), name)
                    && deserialized.isEvil() == isEvil) {
                System.out.println("Two wizards look the same");
            } else {
                System.out.println("Two wizards are somewhat different");
            }
        } catch (IOException e) {
            System.out.println("Didn't happen");
        }
    }
}
