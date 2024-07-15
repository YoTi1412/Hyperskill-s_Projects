package kryowizardry;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import java.io.FileOutputStream;
import java.io.IOException;

class Solution {
    public void doSerialization(Wizard wizard, String filename) throws IOException {
        Kryo kryo = new Kryo();

        // Register the Wizard class with Kryo
        kryo.register(Wizard.class);

        // Open the output stream to write serialized data
        try (Output output = new Output(new FileOutputStream(filename))) {
            // Write the Wizard object to the output stream
            kryo.writeObject(output, wizard);
        }
    }
}
