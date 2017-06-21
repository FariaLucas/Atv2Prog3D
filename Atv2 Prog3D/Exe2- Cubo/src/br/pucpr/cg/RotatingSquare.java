package br.pucpr.cg;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;

import br.pucpr.mage.Mesh;
import br.pucpr.mage.MeshBuilder;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;

public class RotatingSquare implements Scene {
    private Mesh mesh;
    private float angle;

    @Override
    public void init() {
        glClearColor(0.0f, 0.0f, 0.2f, 1.0f);

        mesh = new MeshBuilder()
        .addVector3fAttribute("aPosition", 
                -0.5f,  0.5f, 0.0f, 
                 0.5f,  0.5f, 0.0f, 
                -0.5f, -0.5f, 0.0f, 
                0.5f,  -0.5f, 0.0f)
        .addVector3fAttribute("aColor", 
                1.0f, 0.0f, 0.0f, 
                1.0f, 1.0f, 1.0f, 
                0.0f, 1.0f, 0.0f, 
                0.0f, 0.0f, 1.0f)
        .setIndexBuffer(
                0, 2, 3, 
                0, 3, 1)
        .loadShader("/br/pucpr/resource/basic")
        .create();
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        mesh.setUniform("uWorld", new Matrix4f().rotateY(angle));
        mesh.draw();
    }

    @Override
    public void update(float secs) {
        angle += Math.toRadians(180) * secs;
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new RotatingSquare()).show();
    }
}
