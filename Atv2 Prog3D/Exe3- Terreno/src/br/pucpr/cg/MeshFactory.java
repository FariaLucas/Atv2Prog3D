package br.pucpr.cg;

import org.joml.Vector3f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Random;

public class MeshFactory {



    public static Mesh loadTerreno(File path, float scale) throws IOException {
        BufferedImage img = ImageIO.read(path);
        java.util.List<Vector3f> position = new ArrayList<>();


        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color cor = new Color(img.getRGB(j, i));
                position.add(new Vector3f(j - img.getWidth() * 0.5f, cor.getGreen() * scale, i - img.getHeight() * 0.5f));

            }
        }

        java.util.List<Integer> indice = new ArrayList<>();
        for (int i = 0; i < img.getHeight() - 1; i++) {
            for (int j = 0; j < img.getWidth() - 1; j++) {
                int Zero = j + img.getWidth() * i;
                int Um = j + 1 + img.getWidth() * i;
                int Dois = j + img.getWidth() * (i + 1);
                int Tres = j + 1 + img.getWidth() * (i + 1);
                indice.add(Zero);
                indice.add(Dois);
                indice.add(Tres);
                indice.add(Zero);
                indice.add(Tres);
                indice.add(Um);
            }
        }
        java.util.List<Vector3f> cores = new ArrayList<>();

        Random rand = new Random();
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                float r = rand.nextFloat()/4;
                float g = rand.nextFloat()/4;
                float b = rand.nextFloat();
                cores.add(new Vector3f(r, g, b));
            }
        }

        return new MeshBuilder().addVector3fAttribute("aPosition", position).addVector3fAttribute("aColor", cores).setIndexBuffer(indice).loadShader("/br/pucpr/resource/basic").create();
    }
}