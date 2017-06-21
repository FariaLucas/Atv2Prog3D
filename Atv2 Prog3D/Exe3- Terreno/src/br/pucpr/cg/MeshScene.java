package br.pucpr.cg;

import br.pucpr.mage.Keyboard;
import br.pucpr.mage.Mesh;
import br.pucpr.mage.Scene;
import br.pucpr.mage.Window;
import org.joml.Matrix4f;

import java.io.File;
import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Lucas on 15/05/2017.
 */

public class MeshScene implements Scene {
    private Keyboard keys = Keyboard.getInstance();

    private Mesh mesh;
    private float angle;
    private Camera camera = new Camera();

    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        camera.getPosition().set(0.0f,50.0f,200.0f);

        try {
            //mesh = MeshFactory.loadTerreno(new File("./DBZ-Black.png"), 0.5f);
            //mesh = MeshFactory.loadTerreno(new File("./chess.jpg"), 0.5f);
            mesh = MeshFactory.loadTerreno(new File("./volcano.png"), 0.5f);
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    @Override
    public void update(float secs) {

        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angle += Math.toRadians(180) * secs;
        }
        if (keys.isDown(GLFW_KEY_D)) {
            angle -= Math.toRadians(180) * secs;
        }
        if (keys.isDown(GLFW_KEY_W)) {
            camera.getPosition().y += 100 * secs;
        }
        if (keys.isDown(GLFW_KEY_S)) {
            camera.getPosition().y -= 100 * secs;
        }
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        mesh.getShader().bind()
                .setUniform("uProjection", camera.getProjectionMatrix())
                .setUniform("uView", camera.getViewMatrix())
                .unbind();
        mesh.setUniform("uWorld", new Matrix4f().rotateY(angle));
        mesh.draw();
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args){
        new Window(new MeshScene(), "Terreno", 800, 600).show();
    }
}

