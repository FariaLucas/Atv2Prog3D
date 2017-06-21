package br.pucpr.cg;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import br.pucpr.mage.*;
import br.pucpr.mage.phong.PositionalLight;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import br.pucpr.mage.phong.DirectionalLight;
import br.pucpr.mage.phong.Material;

import java.io.File;
import java.io.IOException;

public class TexturedQuads implements Scene {
    private static final String PATH = "c:/temp/img/opengl/textures/";
    private Keyboard keys = Keyboard.getInstance();
    private boolean normals = false;
    
    //Dados da cena
    private Camera camera = new Camera();
    private PositionalLight light;

    //Dados da malha
    private Mesh mesh;
    private Material material;
    
    private float angleX = 0.0f;
    private float angleY = 0.0f;
    
    @Override
    public void init() {
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_CULL_FACE);
        glPolygonMode(GL_FRONT_FACE, GL_LINE);
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

        camera.getPosition().y = 100;

        camera.getPosition().z = 0.9f;
        light = new PositionalLight(
                new Vector3f( 0f, 250f, 0f),      //position
                new Vector3f( 1.0f,  1.0f,  0.0f),//ambient
                new Vector3f( 1.0f,  1.0f,  1.0f),//diffuse
                new Vector3f( 1.0f,  1.0f,  1.0f),//specular
                0.007f                               //fator

        );
        try{
            mesh = MeshFactory.loadTerrain(new File("C:\\Users\\Lucas\\Desktop\\Terceiro Periodo\\Prog 3D\\img\\opengl\\heights\\river.png"), 0.5f);
        }catch (IOException e){
            e.printStackTrace();
        }

        material = new Material(
                new Vector3f(1.0f, 1.0f, 1.0f), //ambient
                new Vector3f(1.0f, 0f, 0f), //diffuse
                new Vector3f(1f, 1f, 1f), //specular
                128f);                          //Specular power
        //material.setTexture("uTexture", new Texture(PATH + "stone_t.png"));
    }

    @Override
    public void update(float secs) {
        if (keys.isPressed(GLFW_KEY_ESCAPE)) {
            glfwSetWindowShouldClose(glfwGetCurrentContext(), GLFW_TRUE);
            return;
        }

        if (keys.isDown(GLFW_KEY_A)) {
            angleY += secs;
            camera.rotate(0, (float)Math.toRadians(60)*secs);
        }

        if (keys.isDown(GLFW_KEY_D)) {
            angleY -= secs;
            camera.rotate(0, (float)Math.toRadians(-60)*secs);
        }
        
        if (keys.isDown(GLFW_KEY_W)) {
            angleX += secs;
            camera.rotate((float)Math.toRadians(60)*secs, 0);
        }

        if (keys.isDown(GLFW_KEY_S)) {
            angleX -= secs;
            camera.rotate((float)Math.toRadians(-60)*secs, 0);
        }
        
        if (keys.isDown(GLFW_KEY_SPACE)) {
            angleY = 0;
            angleX = 0;
        }
        
        if (keys.isPressed(GLFW_KEY_N)) {
            normals = !normals;
        }
        if (keys.isDown(GLFW_KEY_UP)) {
            camera.moveFront(15 * secs * 2);
        }
        if (keys.isDown(GLFW_KEY_DOWN)) {
            camera.moveFront(-15 * secs * 2);
        }
        if (keys.isDown(GLFW_KEY_LEFT)) {
            camera.strafeLeft(15 * secs * 2);
        }
        if (keys.isDown(GLFW_KEY_RIGHT)) {
            camera.strafeLeft(-15 * secs * 2);
        }
    }

    @Override
    public void draw() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        Shader shader = mesh.getShader();
        shader.bind()
            .setUniform("uProjection", camera.getProjectionMatrix())
            .setUniform("uView", camera.getViewMatrix())
            .setUniform("uCameraPosition", camera.getPosition());        
        light.apply(shader);
        material.apply(shader);
        shader.unbind();
    
        mesh.setUniform("uWorld", new Matrix4f());
        mesh.draw();
    }

    @Override
    public void deinit() {
    }

    public static void main(String[] args) {
        new Window(new TexturedQuads(), "Textures", 1024, 768).show();
    }
}
